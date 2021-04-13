from main import app
from main import dbFavoritos

import unittest

class TestLivros(unittest.TestCase):
    def setUp(self):
        self.app = app.test_client()

    # Testamos se a resposta e 200 ("ok")
    def test_1_get_livros_falha_campos(self):
        self.response = self.app.get('/livros')
        self.assertEqual(400, self.response.status_code)

    def test_2_get_livros_isbn_sucesso(self):
        response = self.app.get('/livros?isbn=9781781104057')
        self.assertEqual(200, response.status_code)
    
    def test_3_get_livros_busca_sucesso(self):
        response = self.app.get('/livros?busca=Financeiro')
        self.assertEqual(200, response.status_code)
    
    def test_4_get_livros_busca_nao_encontrado(self):
        response = self.app.get('/livros?isbn=0192302301230123123912')
        self.assertEqual(404, response.status_code)

class TestLivrosFavoritos(unittest.TestCase):
    def setUp(self):
        self.app = app.test_client()

    def test_1_post_livro(self):
        response = self.app.post('/livros/favoritos', data=dict(
            isbn='9781781104057'
        ))
        self.assertEqual(201, response.status_code)

    def test_2_get_favoritos_status(self):
        response = self.app.get('/livros/favoritos')
        self.assertEqual(200, response.status_code)

    def test_3_get_favoritos_livro(self):
        response = self.app.get('/livros/favoritos')
        self.assertIn('9781781104057', response.get_data(as_text=True))

    def test_4_delete_favoritos(self):
        response = self.app.delete('/livros/favoritos', data=dict(
            isbn='9781781104057'
        ))
        self.assertEqual(200, response.status_code)

class TestComentariosLivros(unittest.TestCase):
    def setUp(self):
        self.app = app.test_client()
        
    def test_1_insere_comentario_falha_campo1(self):
        response = self.app.post('/livros/comentarios', data=dict(
            isbn='9781781104057'
        ))
        self.assertEqual(400, response.status_code)
    
    def test_2_insere_comentario_falha_campo2(self):
        response = self.app.post('/livros/comentarios', data=dict(
            comentario="Muito bom esse livro"
        ))
        self.assertEqual(400, response.status_code)
        self.assertIn('O campo ISBN', response.get_data(as_text=True))

    def test_3_insere_comentario_sucesso(self):
        response = self.app.post('/livros/comentarios', data=dict(
            isbn='9781781104057',
            comentario="Muito bom esse livro"
        ))
        self.assertEqual(201, response.status_code)

    def test_4_get_comentario_falha_campo1(self):
        response = self.app.get('/livros/comentarios')
        self.assertEqual(400, response.status_code)
    
    def test_5_get_comentario_falha_nao_encontrado(self):
        response = self.app.get('/livros/comentarios?isbn=12312312357')
        self.assertEqual(404, response.status_code)

    def test_6_get_comentario_sucesso(self):
        response = self.app.get('/livros/comentarios?isbn=9781781104057')
        self.assertEqual(200, response.status_code)

class TestAvaliacoesLivros(unittest.TestCase):
    def setUp(self):
        self.app = app.test_client()
        
    def test_1_insere_avaliacao_falha_campo1(self):
        response = self.app.post('/livros/avaliacoes', data=dict(
            isbn='9781781104057'
        ))
        self.assertEqual(400, response.status_code)
    
    def test_2_insere_avaliacao_falha_campo2(self):
        response = self.app.post('/livros/avaliacoes', data=dict(
            avaliacao=5
        ))
        self.assertEqual(400, response.status_code)
        self.assertIn('O campo ISBN', response.get_data(as_text=True))

    def test_3_insere_avaliacao_sucesso(self):
        response = self.app.post('/livros/avaliacoes', data=dict(
            isbn='9781781104057',
            avaliacao=5
        ))
        self.assertEqual(201, response.status_code)

    def test_4_get_avaliacao_falha_campo1(self):
        response = self.app.get('/livros/avaliacoes')
        self.assertEqual(400, response.status_code)
    
    def test_5_get_avaliacao_falha_nao_encontrado(self):
        response = self.app.get('/livros/avaliacoes?isbn=12312312357')
        self.assertEqual(404, response.status_code)

    def test_6_get_avaliacao_sucesso(self):
        response = self.app.get('/livros/avaliacoes?isbn=9781781104057')
        self.assertEqual(200, response.status_code)