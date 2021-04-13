package com.javaee.stockmarket.services;

import java.util.Set;

import com.javaee.stockmarket.domain.Order;

public interface OrderService {

	void createNewBuyOrder(Order order);

	void createNewSellOrder(Order order);

	Set<Order> getAll();
	
	void processOrderBuy(Order order);

	void reprocessOrderBuy(String companyName);
}
