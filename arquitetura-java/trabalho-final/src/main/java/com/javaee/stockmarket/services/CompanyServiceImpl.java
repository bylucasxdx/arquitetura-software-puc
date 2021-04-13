package com.javaee.stockmarket.services;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.javaee.stockmarket.domain.Company;
import com.javaee.stockmarket.repository.CompanyRepository;

@Service
public class CompanyServiceImpl implements CompanyService {

	private CompanyRepository companyRepository;
	
	public CompanyServiceImpl(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}
	
	@Override
	public Company createNewCompany(Company company) {
		if (companyRepository.findByCnpj(company.getCnpj()).isEmpty()) {
			return companyRepository.save(company);
		}
		
		throw new IllegalArgumentException("Company already exists with cnpj: " + company.getCnpj());
	}

	@Override
	public Set<Company> getAll() {
		Set<Company> companies = new HashSet<>();
		this.companyRepository.findAll().iterator().forEachRemaining(companies::add);
		return companies;
	}

}
