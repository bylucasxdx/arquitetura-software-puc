package com.javaee.stockmarket.domain;

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
public class Order {

	@Id
	@ApiModelProperty(hidden=true)
	private String id;
	
	@NotNull
	private String investorId;
	
	@NotNull
	private String companyName;
	
	@Min(1)
	private Integer quantity;
	
	@DecimalMin("0.1") 
	private Double price;
	
	@ApiModelProperty(hidden=true)
	private String type;
	
	@Override
    public String toString() {
        return String.format(
                "Order[investor=%s, company='%s', quantity='%s', price='%s', type='%s']",
                investorId, companyName, quantity, price, type);
    }
}
