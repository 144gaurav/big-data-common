package com.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bean.ExchangeValue;

@Repository
public interface ExchangeValueRepo extends JpaRepository<ExchangeValue, Long>{

	Optional<ExchangeValue> findByFromAndTo(String from, String to);
}
