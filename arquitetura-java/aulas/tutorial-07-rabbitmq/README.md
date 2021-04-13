Criação de uma aplicação de filas e mensagens usando Spring e RabbitMQ

# AMQP
- Um protocolo para filas (RabbitMQ)
- Só possui queues que são consumidas por um único receiver.
- Message Broker Open Source 

Podemos criar diversas filas usando o RabbitMQ e devemos vincular a nossa fila a um exchange

#### Observações:
- Podemos configurar diversas filas, é importante criar uma fila para possiveis erros, as dead-queues e através da configuração do RabbitMQ podemos configurar o reenvio dessas mensagens que tiveram problemas.

