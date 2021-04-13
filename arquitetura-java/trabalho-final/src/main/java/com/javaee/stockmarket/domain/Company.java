package com.javaee.stockmarket.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Company {

	@Id
	@ApiModelProperty(hidden=true)
	private String id;
	
	private String name;
	private String cnpj;
	
	public Company() {}
	
	public Company(String name, String cnpj) {
        this.name = name;
        this.cnpj = cnpj;
    }
	
	@Override
    public String toString() {
        return String.format(
                "Company[id=%s, firstName='%s', lastName='%s']",
                id, name, cnpj);
    }
}
