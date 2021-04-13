package com.javaee.stockmarket.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.javaee.stockmarket.config.RabbitMQConfig;
import com.javaee.stockmarket.domain.Action;
import com.javaee.stockmarket.repository.ActionRepository;

@Service
public class ActionServiceImpl implements ActionService {

	private ActionRepository actionRepository;
	private final RabbitTemplate rabbitTemplate;
	
	public ActionServiceImpl(RabbitTemplate rabbitTemplate,
			ActionRepository actionRepository) {
		this.rabbitTemplate = rabbitTemplate;
		this.actionRepository = actionRepository;
	}
	
	@Override
	public void createNewActions(Action action) {
		this.rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NEW_ACTIONS, action);
	}
	
	@Override
	public Action createNewAction(Action action) {
		for (int i = 1; i <= action.getQuantity(); i++) {
			Action actionSplit = new Action();
			actionSplit.setCompany(action.getCompany());
			actionSplit.setPrice(action.getPrice());
			actionSplit.setQuantity(1);
			actionSplit.setOwnerId(action.getCompany().getId());
			actionSplit.setSelling(true);
			actionRepository.save(actionSplit);
		}
		
		return action;
	}

	@Override
	public Set<Action> getAll() {
		Set<Action> actions = new HashSet<>();
		this.actionRepository.findAll().iterator().forEachRemaining(actions::add);
		return actions;
	}

	@Override
	public void updateActionsSelling(String companyName, String investorId, Double price) {
		List<Action> listActionsOwner = actionRepository.findByCompanyNameAndOwner(
				companyName, 
				investorId
		);
		
		for (Action actionSell: listActionsOwner) {
			actionSell.setSelling(true);
			actionSell.setPrice(price);
			actionRepository.save(actionSell);
		}
	}
	
}
