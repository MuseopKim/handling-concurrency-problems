package com.handlingconcurrencyproblems.service;

import static org.assertj.core.api.BDDAssertions.then;

import com.handlingconcurrencyproblems.domain.Stock;
import com.handlingconcurrencyproblems.repository.StockRepository;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PessimisticLockStockServiceTest {

    @Autowired
    private PessimisticLockStockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @BeforeEach
    void beforeEach() {
        Stock stock = new Stock(1L, 100L);
        stockRepository.saveAndFlush(stock);
    }

    @AfterEach
    void afterEach() {
        stockRepository.deleteAll();
    }

    @Test
    void stockDecreaseTest() {
        stockService.decrease(1L, 1L);

        // 100 - 1 = 99;
        Stock stock = stockRepository.findById(1L).orElseThrow();
        then(stock.getQuantity()).isEqualTo(99L);
    }

    @Test
    void concurrentStockDecreaseTest() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockService.decrease(1L, 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Stock stock = stockRepository.findById(1L).orElseThrow();
        then(stock.getQuantity()).isZero();
    }
}