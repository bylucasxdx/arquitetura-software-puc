package com.javaee.stockmarket.controllers.v1;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.javaee.stockmarket.domain.Action;
import com.javaee.stockmarket.services.ActionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="Action controller")
@RestController
@RequestMapping(ActionController.BASE_URL)
public class ActionController {

	public static final String BASE_URL = "api/v1/actions";
	
	private final ActionService actionService;
	
	public ActionController(ActionService actionService) {
		this.actionService = actionService;
	}
	
	@ApiOperation(value="Get all the actions")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Set<Action> getAll() {
		return actionService.getAll();
	}
	
	@ApiOperation(value="Create a new action - Only companies")
	@PostMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void create(@Valid @RequestBody Action action) {
		actionService.createNewActions(action);
	}
	
}
