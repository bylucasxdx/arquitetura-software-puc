package com.javaee.stockmarket.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.javaee.stockmarket.domain.Investor;
import com.javaee.stockmarket.repository.InvestorRepository;

@Service
public class InvestorServiceImpl implements InvestorService {

	private InvestorRepository investorRepository;
	
	public InvestorServiceImpl(InvestorRepository investorRepository) {
		this.investorRepository = investorRepository;
	}
	
	@Override
	public Set<Investor> getAll() {
		Set<Investor> investors = new HashSet<>();
		this.investorRepository.findAll().iterator().forEachRemaining(investors::add);
		return investors;
	}

	@Override
	public Investor getInvestorById(String id) {
		return getById(id);
	}
	
	private Investor getById(String id) {
		Optional<Investor> investorOptional = investorRepository.findById(id);

        if (!investorOptional.isPresent()) {
            throw new IllegalArgumentException("Investor not found for ID: " + id.toString() );
        }
        
        return investorOptional.get();
	}

	@Override
	public Investor createNewInvestor(Investor investor) {
		if (investorRepository.findByCpf(investor.getCpf()).isEmpty()) {
			return investorRepository.save(investor);
		}
		
		throw new IllegalArgumentException("Investor already exists with cpf: " + investor.getCpf());
	}

	@Override
	public Investor saveInvestor(String id, Investor investor) {
		investor.setId(id);
		Investor investorSaved = investorRepository.save(investor);
		return investorSaved;
	}

	@Override
	public void deleteInvestorById(String id) {
		investorRepository.deleteById(id);		
	}

	@Override
	public List<Investor> getInvestorsById(String[] investorsId) {
		List<Investor> investors = new ArrayList<Investor>();
		
		for (String investorId: investorsId) {
			investors.add(getById(investorId));
		}
		
		return investors;
	}

}
