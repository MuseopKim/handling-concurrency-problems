package com.handlingconcurrencyproblems.service;

import com.handlingconcurrencyproblems.domain.Stock;
import com.handlingconcurrencyproblems.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PessimisticLockStockService {

    private final StockRepository stockRepository;

    @Transactional
    public void decrease(long id, long quantity) {
        Stock stock = stockRepository.findByIdWithPessimisticLock(id);
        stock.decrease(quantity);
        stockRepository.saveAndFlush(stock);
    }
}
