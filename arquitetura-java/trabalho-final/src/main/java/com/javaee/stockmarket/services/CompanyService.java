package com.javaee.stockmarket.services;

import java.util.Set;

import com.javaee.stockmarket.domain.Company;

public interface CompanyService {

	Company createNewCompany(Company company);

	Set<Company> getAll();
	
}
