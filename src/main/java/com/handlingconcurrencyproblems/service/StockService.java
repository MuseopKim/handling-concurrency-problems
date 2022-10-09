package com.handlingconcurrencyproblems.service;

import com.handlingconcurrencyproblems.domain.Stock;
import com.handlingconcurrencyproblems.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;

//    @Transactional
    public synchronized void decrease(Long id, Long quantity) {
        Stock stock = stockRepository.findById(id).orElseThrow();
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }
}
