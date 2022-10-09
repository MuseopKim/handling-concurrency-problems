package com.handlingconcurrencyproblems.proxy;

import com.handlingconcurrencyproblems.service.StockService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ProxyStockService {

    private final StockService stockService;

    public void decrease(Long id, Long quantity) {
        begin();

        try {
            stockService.decrease(id, quantity);
        } catch (Exception exception) {
            rollback();
        }

        commit();
    }

    public void begin() {

    }

    public void commit() {

    }

    public void rollback() {

    }
}
