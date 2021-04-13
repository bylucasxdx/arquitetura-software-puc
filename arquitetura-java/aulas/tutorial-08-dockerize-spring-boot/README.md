### Criação de um container para a aplicação Spring

Foco no Dockerfile arquivo de configuração para montar o container

Depois de ter o arquivo criado é só executar os comandos abaixo
```
# Para criar uma nova imagem do docker é necessário rodar o comando abaixo:
'docker build -t [imageName] .'

# Para rodar a imagem é necessário executar o comando abaixo:
docker run -d -p 8080:8080 imageName
```

***
#### Observações:
- No comando docker build tem que colocar o "." no final para executar o código
