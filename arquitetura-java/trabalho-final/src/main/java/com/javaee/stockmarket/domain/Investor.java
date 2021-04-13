package com.javaee.stockmarket.domain;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document
public class Investor {

	@Id
	@ApiModelProperty(hidden=true)
	private String id;
	
	@NotNull
	private String name;
	
	@NotNull
	private String email;
	
	@NotNull
	private String cpf;
	
	@Override
    public String toString() {
        return String.format(
                "Investor[id=%s, name='%s', cpf='%s']",
                id, name, cpf);
    }
}
