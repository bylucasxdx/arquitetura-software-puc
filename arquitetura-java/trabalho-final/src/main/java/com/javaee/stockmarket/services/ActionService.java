package com.javaee.stockmarket.services;

import java.util.Set;

import com.javaee.stockmarket.domain.Action;

public interface ActionService {

	Action createNewAction(Action action);
	
	Set<Action> getAll();
	
	void updateActionsSelling(String companyName, String investorId, Double price);

	void createNewActions(Action action);
	
}
