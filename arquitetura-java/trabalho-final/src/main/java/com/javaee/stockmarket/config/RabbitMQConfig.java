package com.javaee.stockmarket.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListenerConfigurer;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistrar;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.support.DefaultMessageHandlerMethodFactory;
import org.springframework.messaging.handler.annotation.support.MessageHandlerMethodFactory;

@Configuration
public class RabbitMQConfig implements RabbitListenerConfigurer {

	public static final String QUEUE_ORDER_BUY = "order-buy-queue";
	public static final String EXCHANGE_ORDER_BUY = "order-buy-exchange";
	public static final String QUEUE_DEAD_ORDER_BUY = "order-buy-dead-queue";
	
	public static final String QUEUE_ORDER_SELL = "order-sell-queue";
	public static final String EXCHANGE_ORDER_SELL = "order-sell-exchange";
	public static final String QUEUE_DEAD_ORDER_SELL = "order-sell-dead-queue";
	
	public static final String QUEUE_NEW_ACTIONS = "new-actions-queue";
	public static final String EXCHANGE_NEW_ACTIONS = "new-actions-exchange";
	
	public static final String QUEUE_EMAIL = "email-queue";
	public static final String EXCHANGE_EMAIL = "email-exchange";
	public static final String QUEUE_DEAD_EMAIL = "email-dead-queue";
	
	@Bean
    Queue orderBuyQueue() {
    	return QueueBuilder.durable(QUEUE_ORDER_BUY)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_DEAD_ORDER_BUY)
                .withArgument("x-message-ttl", 15000)
                .build();
	}
	
	@Bean
    Queue deadOrderBuyQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_ORDER_BUY).build();
	}
	
	@Bean
    Exchange orderBuyExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_ORDER_BUY).build();
    }
	
	@Bean
    Binding bindingOrderBuy(Queue orderBuyQueue, TopicExchange orderBuyExchange) {
		return BindingBuilder.bind(orderBuyQueue).to(orderBuyExchange).with(QUEUE_ORDER_BUY);
    }

	@Bean
    Queue orderSellQueue() {
    	return QueueBuilder.durable(QUEUE_ORDER_SELL)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_DEAD_ORDER_SELL)
                .withArgument("x-message-ttl", 15000) //if message is not consumed in 15 seconds send to DLQ
                .build();
	}
	
	@Bean
    Queue deadOrderSellQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_ORDER_SELL).build();
	}
	
	@Bean
    Exchange orderSellExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_ORDER_SELL).build();
    }
	
	@Bean
    Binding bindingOrderSell(Queue orderSellQueue, TopicExchange orderSellExchange) {
		return BindingBuilder.bind(orderSellQueue).to(orderSellExchange).with(QUEUE_ORDER_SELL);
    }
	
	@Bean
    Queue newActionsQueue() {
    	return QueueBuilder.durable(QUEUE_NEW_ACTIONS)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-message-ttl", 15000)
                .build();
	}
	
	@Bean
    Exchange newActionsExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_NEW_ACTIONS).build();
    }
	
	@Bean
    Binding bindingNewActions(Queue newActionsQueue, TopicExchange newActionsExchange) {
		return BindingBuilder.bind(newActionsQueue).to(newActionsExchange).with(QUEUE_NEW_ACTIONS);
    }
	
	@Bean
    Queue emailQueue() {
    	return QueueBuilder.durable(QUEUE_EMAIL)
                .withArgument("x-dead-letter-exchange", "")
                .withArgument("x-dead-letter-routing-key", QUEUE_DEAD_EMAIL)
                .withArgument("x-message-ttl", 15000)
                .build();
	}
	
	@Bean
    Queue deadEmailQueue() {
        return QueueBuilder.durable(QUEUE_DEAD_EMAIL).build();
	}
	
	@Bean
    Exchange emailExchange() {
        return ExchangeBuilder.topicExchange(EXCHANGE_EMAIL).build();
    }
	
	@Bean
    Binding bindingEmail(Queue emailQueue, TopicExchange emailExchange) {
		return BindingBuilder.bind(emailQueue).to(emailExchange).with(QUEUE_ORDER_BUY);
    }
	
    @Bean
    public RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(producerJackson2MessageConverter());
        return rabbitTemplate;
    }
	
    @Bean
    public Jackson2JsonMessageConverter producerJackson2MessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
    
    @Override
    public void configureRabbitListeners(RabbitListenerEndpointRegistrar register) {
        register.setMessageHandlerMethodFactory(messageHandlerMethodFactory());
    }
 
    @Bean
    MessageHandlerMethodFactory messageHandlerMethodFactory() {
        DefaultMessageHandlerMethodFactory messageHandlerMethodFactory = new DefaultMessageHandlerMethodFactory();
        messageHandlerMethodFactory.setMessageConverter(consumerJackson2MessageConverter());
        return messageHandlerMethodFactory;
    }

    @Bean
    public MappingJackson2MessageConverter consumerJackson2MessageConverter() {
        return new MappingJackson2MessageConverter();
    }
}
