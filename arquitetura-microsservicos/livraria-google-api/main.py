from flask import Flask
from flask import request
from json_encoder import json

import os
import requests
from credentials import keyGoogle

app = Flask(__name__)

# Inicializa um banco de dados
from tinydb import TinyDB, Query
dbFavoritos = TinyDB('dbFavoritos.json')
dbOpinioes = TinyDB('dbOpinioes.json')

# Apaga todo o conteudo para inciar uma nova sessão
dbFavoritos.purge()
dbOpinioes.purge()

@app.route("/livros", methods=["GET"])
def searchBooks():
    retornoGoogle = None

    if (request.args.get('isbn') is not None):
        isbn = request.args.get('isbn')
        retornoGoogle = json.loads(buscaGoogle(isbn))
    
    if (request.args.get('busca') is not None and retornoGoogle is None):
        busca = request.args.get('busca')
        retornoGoogle = json.loads(buscaGoogle(False, busca))

    if (retornoGoogle is None):
        return json.dumps({
            "tipo": "alert",
            "mensagem": "Você deve enviar sua busca ou isbn para encontrar livros"
        }), 400, {"Content-Type": "application/json"}

    if ('items' in retornoGoogle):
        return json.dumps({
            "tipo": "success",
            "livros": retornoGoogle['items']
        }), 200, {"Content-Type": "application/json"}
    else :
        return json.dumps({
            "tipo": "alert",
            "mensagem": "Nenhum livro encontrado com esse ISBN!"
        }), 404, {"Content-Type": "application/json"}
    
@app.route("/livros/favoritos", methods=["POST"])
def addFavoritos():
    if (request.form.get('isbn') is not None):
        isbn = request.form.get('isbn')
        retornoGoogle = json.loads(buscaGoogle(isbn))

        if ('items' in retornoGoogle):
            dbFavoritos.insert(retornoGoogle['items'][0])
            return json.dumps({
                "tipo": "success",
                "mensagem": "Livro adicionado na lista de favoritos!"
            }), 201, {"Content-Type": "application/json"}
        else:
            return json.dumps({
                "tipo": "alert",
                "mensagem": "Nenhum livro encontrado com esse ISBN!"
            }), 400, {"Content-Type": "application/json"}
    else :
        return json.dumps({
            "tipo": "alert",
            "mensagem": "O campo ISBN é obrigátorio!"
        }), 400, {"Content-Type": "application/json"}

@app.route('/livros/favoritos', methods=["GET"])
def getFavoritos():
    return json.dumps(dbFavoritos.all()), 200, {"Content-Type": "application/json"}

@app.route('/livros/favoritos', methods=["DELETE"])
def deleteFavoritos():
    if (request.form.get('isbn') is not None):
        isbn = request.form.get('isbn')
        livro = dbFavoritos.search(
            Query().volumeInfo.industryIdentifiers[0].identifier == isbn)
        if (livro):
            dbFavoritos.remove(
                Query().volumeInfo.industryIdentifiers[0].identifier == isbn)
            return json.dumps({
                "tipo": "success",
                "mensagem": "Livro removido dos favoritos"
            }), 200, {"Content-Type": "application/json"}
        else:
            return json.dumps({
                "tipo": "alert",
                "mensagem": "Livro não encontrado nos favoritos"
            }), 404, {"Content-Type": "application/json"}
    else:
        return json.dumps({
            "tipo": "alert",
            "mensagem": "É necessário enviar o ISBN do livro"
        }), 400, {"Content-Type": "application/json"}
    
@app.route('/livros/comentarios', methods=["POST"])
def addComentarios():
    if (request.form.get('isbn') is None):
        return json.dumps({
            "tipo": "alert",
            "mensagem": "O campo ISBN é obrigátorio!"
        }), 400, {"Content-Type": "application/json"}
    
    if (request.form.get('comentario') is None):
        return json.dumps({
            "tipo": "alert",
            "mensagem": "É necessário enviar o seu comentário para esse livro"
        }), 400, {"Content-Type": "application/json"}
    
    livro = dbOpinioes.search(
        Query()['isbn'] == request.form.get('isbn')
    )

    if (len(livro)):
        livro[0]['comentarios'].append(request.form.get('comentario'))
        dbOpinioes.write_back(livro)    
    else:
        comentario = {
            "isbn": request.form.get('isbn'), 
            "comentarios": [request.form.get('comentario')],
            "avaliacoes": []
        }
        dbOpinioes.insert(comentario)
    
    return json.dumps({
        "tipo": "success",
        "mensagem": "Comentário adicionado com sucesso!"
    }), 201, {"Content-Type": "application/json"}

@app.route('/livros/comentarios', methods=["GET"])
def getComentarios():
    if (request.args.get('isbn') is None):
        return json.dumps({
            "tipo": "alert",
            "mensagem": "O campo ISBN é obrigátorio!"
        }), 400, {"Content-Type": "application/json"}

    comentarios = dbOpinioes.search(
        Query()['isbn'] == request.args.get('isbn')
    )

    if (len(comentarios)):
        comentarios[0]['tipo'] = 'success'
        return json.dumps(
            comentarios
        ), 200, {"Content-Type": "application/json"}
    else:
        return json.dumps({
            "tipo": "alert",
            "mensagem": "Nenhum comentário encontrado para esse livro!"
        }), 404, {"Content-Type": "application/json"}
        
@app.route('/livros/avaliacoes', methods=["POST"])
def addAvalicacao():
    if (request.form.get('isbn') is None):
        return json.dumps({
            "tipo": "alert",
            "mensagem": "O campo ISBN é obrigátorio!"
        }), 400, {"Content-Type": "application/json"}

    if (request.form.get('avaliacao') is None):
        return json.dumps({
            "tipo": "alert",
            "mensagem": "É necessário enviar a sua avaliação para esse livro"
        }), 400, {"Content-Type": "application/json"}

    livro = dbOpinioes.search(
        Query()['isbn'] == request.form.get('isbn')
    )

    if (len(livro)):
        livro[0]['avaliacoes'].append(request.form.get('avaliacao'))
        dbOpinioes.write_back(livro)
    else:
        comentario = {
            "isbn": request.form.get('isbn'),
            "comentarios": [],
            "avaliacoes": [request.form.get('avaliacao')]
        }
        dbOpinioes.insert(comentario)

    return json.dumps({
        "tipo": "success",
        "mensagem": "Avaliação adicionada com sucesso!"
    }), 201, {"Content-Type": "application/json"}

@app.route('/livros/avaliacoes', methods=["GET"])
def getAvaliacoes():
    if (request.args.get('isbn') is None):
        return json.dumps({
            "tipo": "alert",
            "mensagem": "O campo ISBN é obrigátorio!"
        }), 400, {"Content-Type": "application/json"}

    avaliacoes = dbOpinioes.search(
        Query()['isbn'] == request.args.get('isbn')
    )

    if (len(avaliacoes)):
        avaliacoes[0]['tipo'] = 'success'
        return json.dumps(
            avaliacoes
        ), 200, {"Content-Type": "application/json"}
    else:
        return json.dumps({
            "tipo": "alert",
            "mensagem": "Nenhuma avaliação encontrada para esse livro!"
        }), 404, {"Content-Type": "application/json"}

def buscaGoogle(isbn = False, busca = False):
    fields = 'kind,items(volumeInfo(title,subtitle,authors,publishedDate,description,industryIdentifiers))'

    if (isbn != False):
        return requests.get('https://www.googleapis.com/books/v1/volumes?q=isbn:{0}&langRestrict=pt&key={1}&fields={2}'
            .format(isbn, keyGoogle, fields)).text

    elif (busca != False):
        return requests.get('https://www.googleapis.com/books/v1/volumes?q={0}&langRestrict=pt&key={1}&fields={2}'
            .format(busca, keyGoogle, fields)).text

    return 'Você não enviou os parâmetros de busca'

if __name__ == "__main__":
    app.run()
    # port = int(os.environ.get("PORT", 5000))
    # app.run(host='0.0.0.0', port=port)
