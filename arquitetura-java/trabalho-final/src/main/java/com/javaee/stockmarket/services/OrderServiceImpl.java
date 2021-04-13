package com.javaee.stockmarket.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.javaee.stockmarket.config.RabbitMQConfig;
import com.javaee.stockmarket.domain.Action;
import com.javaee.stockmarket.domain.Company;
import com.javaee.stockmarket.domain.Investor;
import com.javaee.stockmarket.domain.Message;
import com.javaee.stockmarket.domain.Order;
import com.javaee.stockmarket.repository.ActionRepository;
import com.javaee.stockmarket.repository.CompanyRepository;
import com.javaee.stockmarket.repository.InvestorRepository;
import com.javaee.stockmarket.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {

	private final RabbitTemplate rabbitTemplate;
	private InvestorRepository investorRepository;
	private CompanyRepository companyRepository;
	private OrderRepository orderRepository;
	private ActionRepository actionRepository;
	
	public OrderServiceImpl(InvestorRepository investorRepository, 
			CompanyRepository companyRepository,
			ActionRepository actionRepository,
			OrderRepository orderRepository,
			RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
		this.investorRepository = investorRepository;
		this.actionRepository = actionRepository;
		this.companyRepository = companyRepository;
		this.orderRepository = orderRepository;
	}

	@Override
	public void createNewBuyOrder(Order order) {
		Company company = companyRepository.findByName(order.getCompanyName());
		if (company == null) {
			throw new IllegalArgumentException("Company not found");
		}
		
		Optional<Investor> investor = investorRepository.findById(order.getInvestorId());
		if (!investor.isPresent()) {
			throw new IllegalArgumentException("Investor not found");
		}
		
		order.setType("buy");
		
		this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_ORDER_BUY, order);
	}

	@Override
	public void createNewSellOrder(Order order) {
		order.setType("sell");
		this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_ORDER_SELL, order);
	}
	
	@Override
	public Set<Order> getAll() {
		Set<Order> orders = new HashSet<>();
		this.orderRepository.findAll().iterator().forEachRemaining(orders::add);
		return orders;
	}
	
	@Override
	public void processOrderBuy(Order order) {
		List<Action> listActionsSelling = actionRepository.findByCompanyNameAndSelling(
				order.getCompanyName(), 
				true
		);
		
		for (Action action: listActionsSelling) {
			if (action.getPrice() > order.getPrice()) {
				continue;
			}
			
			if (order.getQuantity() > 0) {				
				sendEmailOrderExecuted(order, action);
				
				action.setOwnerId(order.getInvestorId());
				action.setSelling(false);
				actionRepository.save(action);
				
				order.setQuantity(order.getQuantity() - 1);
				continue;
			} 
			
			break;
		}
		
		if (order.getQuantity() > 0 && order.getId() == null) {
			orderRepository.save(order);
		} else if (order.getId() != null) {			
			orderRepository.delete(order);
		}
	}

	private void sendEmailOrderExecuted(Order order, Action action) {
		Optional<Investor> investorBuy = investorRepository.findById(order.getInvestorId());
		Optional<Investor> investorSell = investorRepository.findById(action.getOwnerId());
		
		List<String> recipients = new ArrayList<String>();
		
		if (investorBuy.isPresent()) {
			Message buyMail = new Message();
			buyMail.setTitle("Order has been executed");
			buyMail.setBody("Congrats, your buy order has been executed!");
			buyMail.setToEmail(investorBuy.get().getEmail());
			this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_EMAIL, buyMail);
		}
		
		if (investorSell.isPresent()) {
			recipients.add(investorSell.get().getEmail());
			Message sellMail = new Message();
			sellMail.setTitle("Order has been executed");
			sellMail.setBody("Congrats, your sell order has been executed");
			sellMail.setToEmail(investorBuy.get().getEmail());
			this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_EMAIL, sellMail);
		}
	}

	@Override
	public void reprocessOrderBuy(String companyName) {
		List<Order> ordersBuy = orderRepository.findByCompanyName(
			companyName, 
			"buy"
		);
		
		for (Order orderBuy: ordersBuy) {
			processOrderBuy(orderBuy);
		}
	}

}
