package com.handlingconcurrencyproblems.facade;

import com.handlingconcurrencyproblems.repository.LockRepository;
import com.handlingconcurrencyproblems.service.OptimisticLockStockService;
import com.handlingconcurrencyproblems.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class NamedLockStockFacade {

    private final LockRepository lockRepository;
    private final StockService stockService;

    @Transactional
    public void decrease(long id, long quantity) throws InterruptedException {
        try {
            lockRepository.getLock(String.valueOf(id));
            stockService.decrease(id, quantity);
        } finally {
            lockRepository.releaseLock(String.valueOf(id));
        }
    }
}
