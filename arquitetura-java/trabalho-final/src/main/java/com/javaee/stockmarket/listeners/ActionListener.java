package com.javaee.stockmarket.listeners;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.javaee.stockmarket.config.RabbitMQConfig;
import com.javaee.stockmarket.domain.Action;
import com.javaee.stockmarket.domain.Order;
import com.javaee.stockmarket.services.ActionService;
import com.javaee.stockmarket.services.OrderService;

@Component
public class ActionListener {

	static final Logger logger = LoggerFactory.getLogger(ActionListener.class);

	private ActionService actionService;
	private OrderService orderService;
	
	public ActionListener(ActionService actionService,
			OrderService orderService) {
		this.actionService = actionService;
		this.orderService = orderService;
	}
	
	@RabbitListener(queues = RabbitMQConfig.QUEUE_ORDER_BUY)
	public void processQueueOrderBuy(Order order) {
		orderService.processOrderBuy(order);
		
		logger.info("Message Received");	
		logger.info(order.getCompanyName());
		logger.info(order.getInvestorId());
		logger.info(order.getPrice().toString());
		logger.info(order.getQuantity().toString());
	}
	
	@RabbitListener(queues = RabbitMQConfig.QUEUE_ORDER_SELL)
	public void processQueueOrderSell(Order orderSell) {
		actionService.updateActionsSelling(orderSell.getCompanyName(), orderSell.getInvestorId(), orderSell.getPrice());
		orderService.reprocessOrderBuy(orderSell.getCompanyName());
		
		logger.info("Order Sell Received");	
		logger.info(orderSell.getCompanyName());
		logger.info(orderSell.getInvestorId());
		logger.info(orderSell.getPrice().toString());
		logger.info(orderSell.getQuantity().toString());
	}
	
	@RabbitListener(queues = RabbitMQConfig.QUEUE_NEW_ACTIONS)
	public void processQueueNewActions(Action action) {
		actionService.createNewAction(action);
		orderService.reprocessOrderBuy(action.getCompany().getName());
		
		logger.info("New actions received");
		logger.info(action.getCompany().toString());
	}
}
