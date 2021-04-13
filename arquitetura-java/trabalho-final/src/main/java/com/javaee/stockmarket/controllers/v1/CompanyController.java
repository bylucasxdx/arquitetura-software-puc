package com.javaee.stockmarket.controllers.v1;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.javaee.stockmarket.domain.Company;
import com.javaee.stockmarket.services.CompanyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="Company controller")
@RestController
@RequestMapping(CompanyController.BASE_URL)
public class CompanyController {

	public static final String BASE_URL = "api/v1/companies";
	
	private CompanyService companyService;
	
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	@ApiOperation(value="Get all the companies")
	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public Set<Company> getAll() {
		return companyService.getAll();
	}

	@ApiOperation(value="Create a new company")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public Company create(@RequestBody Company company) {
		return companyService.createNewCompany(company);
	}
}
