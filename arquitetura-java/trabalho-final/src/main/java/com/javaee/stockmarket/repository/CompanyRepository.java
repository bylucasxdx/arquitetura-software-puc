package com.javaee.stockmarket.repository;

import java.util.Set;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.javaee.stockmarket.domain.Company;

@Repository
public interface CompanyRepository extends MongoRepository<Company, String> {

	
    public Company findByName(String name);
    
    public Set<Company> findByCnpj(String cpnj);

}
