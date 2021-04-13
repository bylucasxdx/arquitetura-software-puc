package com.javaee.stockmarket.services;

import java.util.List;
import java.util.Set;

import com.javaee.stockmarket.domain.Investor;

public interface InvestorService {

	Set<Investor> getAll();

	Investor getInvestorById(String id);
	
	List<Investor> getInvestorsById(String[] investorsId);

	Investor createNewInvestor(Investor investor);

	Investor saveInvestor(String id, Investor investor);

	void deleteInvestorById(String id);
	
}
