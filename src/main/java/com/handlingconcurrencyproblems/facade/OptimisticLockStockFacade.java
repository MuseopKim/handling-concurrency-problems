package com.handlingconcurrencyproblems.facade;

import com.handlingconcurrencyproblems.service.OptimisticLockStockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Component
public class OptimisticLockStockFacade {

    private final OptimisticLockStockService optimisticLockStockService;

    public void decrease(long id, long quantity) throws InterruptedException {
        while(true) {
            try {
                optimisticLockStockService.decrease(id, quantity);
                break;
            } catch (Exception exception) {
                Thread.sleep(50);
            }
        }
    }
}
