package com.javaee.stockmarket.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.javaee.stockmarket.config.RabbitMQConfig;
import com.javaee.stockmarket.domain.Message;
import com.javaee.stockmarket.email.Sender;

@Component
public class EmailListener {

	static final Logger logger = LoggerFactory.getLogger(ActionListener.class);

	private final Sender sender;
	
	public EmailListener(Sender sender) {
		this.sender = sender;
	}
	
	@RabbitListener(queues = RabbitMQConfig.QUEUE_EMAIL)
	public void processQueueEmail(Message message) {
		logger.info("Message Received");	
		logger.info(message.getTitle());
		logger.info(message.getBody());
		logger.info(message.getToEmail());
		
		sender.sendEmail(message.getToEmail(), message.getTitle(), message.getBody());
	}
}
