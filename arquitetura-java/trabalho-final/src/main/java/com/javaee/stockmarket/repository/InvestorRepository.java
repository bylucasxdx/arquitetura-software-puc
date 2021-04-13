package com.javaee.stockmarket.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.stockmarket.domain.Investor;

@Repository
public interface InvestorRepository extends MongoRepository<Investor, String> {

	public Optional<Investor> findById(String id);
	public Investor findByName(String name);
	
	public Set<Investor> findByCpf(String cpf);
	
}
