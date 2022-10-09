package com.handlingconcurrencyproblems.repository;

import com.handlingconcurrencyproblems.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {

}
