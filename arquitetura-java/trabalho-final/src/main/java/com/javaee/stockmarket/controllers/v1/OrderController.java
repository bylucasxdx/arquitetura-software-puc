package com.javaee.stockmarket.controllers.v1;

import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.javaee.stockmarket.domain.Order;
import com.javaee.stockmarket.services.OrderService;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping(OrderController.BASE_URL)
public class OrderController {

	public static final String BASE_URL = "api/v1/orders";
	
	private final OrderService orderService;
	
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}
	
	@ApiOperation(value="Get all pending orders")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Set<Order> getAll() {
		return orderService.getAll();
	}
	
	@ApiOperation(value="Create a new buy order")
	@PostMapping("/buy")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public ResponseEntity<?> buy(@Valid @RequestBody Order order) {
		try {
			orderService.createNewBuyOrder(order);
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value="Create a new sell order")
	@PostMapping("/sell")
	@ResponseStatus(HttpStatus.ACCEPTED)
	public void sell(@Valid @RequestBody Order order) {
		orderService.createNewSellOrder(order);
	}
}
