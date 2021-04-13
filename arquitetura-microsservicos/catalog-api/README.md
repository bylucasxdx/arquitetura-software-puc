# Trabalho Arquitetura de nuvem

## Grupo:
- Lucas Medeiros
- Alexandre Cunha C.Oliveira
- Henrique franzoni keppel
- Prince Sanis

Foco na utilização de Docker para criação de uma arquitetura completa baseada no exemplo da Microsoft:
- https://github.com/dotnet-architecture/eShopOnContainers

Utilizando - Swagger generated server

## Requisitos
Python 3.5.2+

## Uso

Para rodar o servidor localmente, execute os comandos na raiz do diretório:

```
pip3 install -r requirements.txt
python3 -m swagger_server
```

Para testar use o seguinte endereço:

```
http://localhost:8080/bylucasxdx/CatalogApi/1.0.0/ui/
```

O código swagger fica neste endereço:

```
http://localhost:8080/bylucasxdx/CatalogApi/1.0.0/swagger.json
```

## Rodando com Docker

Para rodar o servidor em um container Docker, execute os comandos na raiz do diretório:

```bash
# building the image
docker build -t swagger_server .

# starting up a container
docker run -p 8080:8080 swagger_server
```