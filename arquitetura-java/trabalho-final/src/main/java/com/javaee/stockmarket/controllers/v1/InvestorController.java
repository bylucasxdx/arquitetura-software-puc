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

import com.javaee.stockmarket.domain.Investor;
import com.javaee.stockmarket.services.InvestorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="Investor controller")
@RestController
@RequestMapping(InvestorController.BASE_URL)
public class InvestorController {

	public static final String BASE_URL = "api/v1/investors";
	
	private final InvestorService investorService;
	
	public InvestorController(InvestorService investorService) {
		this.investorService = investorService;
	}
	
	@ApiOperation(value="Get all the investor")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Set<Investor> getAll() {
		return investorService.getAll();
	}
	
	@ApiOperation(value="Create a new investor")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Investor create(@Valid @RequestBody Investor investor) {
		return investorService.createNewInvestor(investor);
	}
	
}
