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
PHP 5.5+

## Uso

Para rodar o servidor localmente, execute os comandos na raiz do diretório:

```
composer install
```

Para testar use o seguinte endereço:

```
http://localhost:8080/index.php/bylucasxdx/LocationsApi/1.0.0/localizacoes
```

## Rodando com Docker

Para rodar o servidor em um container Docker, execute os comandos na raiz do diretório:

```bash
# building the image
docker build -t locationapi:v1 .

# starting up a container
docker run -it --rm --name locationapi01 -p 8080:80 locationapi:v1
```