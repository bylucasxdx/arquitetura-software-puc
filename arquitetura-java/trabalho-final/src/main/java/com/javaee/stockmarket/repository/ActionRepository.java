package com.javaee.stockmarket.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.javaee.stockmarket.domain.Action;
import com.javaee.stockmarket.domain.Company;

@Repository
public interface ActionRepository extends MongoRepository<Action, String> {

	public Action findByCompanyId(String companyId);
	
	@Query("{'company.name' : ?0, selling: ?1}")
	public List<Action> findByCompanyNameAndSelling(String companyName, Boolean selling);

	public List<Action> findByCompany(Company company);

	@Query("{'company.name' : ?0, ownerId: ?1}")
	public List<Action> findByCompanyNameAndOwner(String companyName, String ownerId);
	
}
