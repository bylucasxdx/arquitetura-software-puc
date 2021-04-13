package com.javaee.stockmarket.domain;

import java.util.UUID;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Action {

	@Id
	@ApiModelProperty(hidden=true)
	private String id;
	
	@NotNull
	private Company company;
	
	@NotNull
	@DecimalMin("0.01")
	private Double price;

	@NotNull
	@Min(1)
	private Integer quantity;
	
	@ApiModelProperty(hidden=true)
	private String ownerId;
	
	@ApiModelProperty(hidden=true)
	private Boolean selling;
	
	public Action() {}
	
	public Action(Company company, Double price, Integer quantity) {
		this.id = UUID.randomUUID().toString();
		this.company = company;
		this.price = price;
		this.quantity = quantity;
	}
	
	@Override
    public String toString() {
        return String.format(
                "Action[id=%s, price='%s', quantity='%s']",
                id, price, quantity);
    }
}
