package com.javaee.stockmarket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.javaee.stockmarket.domain.Order;

@Repository
public interface OrderRepository extends MongoRepository<Order, String> {

	public Optional<Order> findById(String id);
	
	@Query("{companyName: ?0, type: ?1}")
	public List<Order> findByCompanyName(String companyName, String type);

}
