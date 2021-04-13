#### Projeto final - Arquitetura de Software na plataforma Java EE

Para iniciar o projeto é necessário ter instalado alguns componentes

- [Eclipse](https://www.eclipse.org/downloads/)
- [RabbitMQ](https://www.rabbitmq.com/download.html)
- [MongoDB Server](https://www.mongodb.com/download-center)

```
# Depois de instalar o RabbitMQ abra o command prompt
# e inicialize o serviço usando o seguinte comando:
rabbitmq-plugins.bat enable rabbitmq_management
```

Para configurar a parte do envio de email é necessário entrar no arquivo config.properties e alterar os campos: email, password e name.

Caso tenha o docker instalado você pode subir uma instância do MongoDB e outra do RabbitMQ
seguem os comandos padrões para inicializar os containers

```
docker run -d --hostname rabbitmq --name rabbitmq-management -p 15672:15672 -p 5671:5671 -p 5672:5672 rabbitmq:management

docker run -p 27017:27017 --name mongodb -d mongo
```

Inicialize o projeto como Java Application, quando acabar o processo de inicialização entre na documentação do swagger vinculada ao projeto:
[Acessar API](http://localhost:8080/swagger-ui.html)

Base Apis:
- actions
- companies
- investors
- orders
- message

Data structure:
```
company: {
	id: "...",
	name: "...",
	cnpj: "...",
}

action: {
	id: "...",
	company: company,
	quantity: 0,
	price: 0.00,
	ownerId: "..."
	selling: boolean
}

investor: {
	id: "...",
	name: "...",
	email: "...",
	cpf: "...",
}

order: {
	id: "...",
	investorId: "...",
	companyName: "...",
	quantity: 0,
	price: 0.00,
	type: [sell/buy]
}
```

Uma inicialização rápida vai ser executada no start do projeto (ApplicationBootstrap), nela você já terá eventos de criação de empresas, lotes de ações e usuários.

Como funciona?

- Cadastre uma empresa ou utilize as já cadastradas
- Você pode inserir novas ações passando um objeto empresa, a quantidade e o preço das ações. (Estou enviando o objeto completo, porém se fosse em um sistema com credenciais ele já estaria vinculado e a empresa só teria o trabalho de enviar a quantidade e o preço do lote)
- Na compra é necessário mandar o nome da empresa e o Id do investidor, realizo algumas validações usando anotações e a classe domain.
- A compra, venda, lançamento de novas ações e email estão vinculadas ao RabbitMQ cada uma com sua respectiva fila de execução.
- Na parte da compra caso ela não seja executada por completo eu salvo no banco para processar em outros momentos. (Venda / Novas ações sendo lançadas)
- Na parte da venda eu realizo o reprocessamento da fila de compra, buscando as informações do banco e verificando se o negócio estará de fato fechado)
- Na criação de novas ações eu também reprocesso a fila de compra.
- O email é enviado sempre em cada ação, então cuidado ao executar a compra de multiplas. Deixei assim num primeiro momento pois poderiam existir diversos vendedores e eu queria notificar todos assim que suas vendas fossem executadas.