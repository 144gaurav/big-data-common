package com.controllers;

import java.math.BigDecimal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bean.ExchangeValue;
import com.repo.ExchangeValueRepo;

@RestController
public class ExchangeValueController {

	@GetMapping("/exchange-value-dummy/from/{from}/to/{to}")
	public ExchangeValue getValue(@PathVariable String from, @PathVariable String to) {
		
		return new ExchangeValue(1l,from,to,BigDecimal.valueOf(1234));
	}
	
	@Autowired
	ExchangeValueRepo repo;
	
	@Autowired
	Environment env;
	@GetMapping("/exchange-value/from/{from}/to/{to}")
	public ExchangeValue getValue1(@PathVariable String from, @PathVariable String to) {
		Optional<ExchangeValue> optional = repo.findByFromAndTo(from, to);
		if(optional.isPresent())
		{
			ExchangeValue value = optional.get();
			value.setPort(Integer.parseInt(env.getProperty("local.server.port")));
			return value;
		}
		
		return new ExchangeValue(1l,from,to,BigDecimal.valueOf(1234));
	}
	
	
}
