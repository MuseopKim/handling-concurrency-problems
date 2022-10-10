package com.handlingconcurrencyproblems.facade;

import com.handlingconcurrencyproblems.service.StockService;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class RedissonLockStockFacade {

    private final RedissonClient redissonClient;
    private final StockService stockService;

    public void decrease(long key, long quantity) {
        RLock lock = redissonClient.getLock(String.valueOf(key));

        try {
            boolean available = lock.tryLock(5, 1, TimeUnit.SECONDS);

            if (!available) {
                log.info("get lock failed");
                return;
            }

            stockService.decrease(key, quantity);
        } catch (InterruptedException exception) {
            throw new RuntimeException(exception);
        } finally {
            lock.unlock();
        }
    }
}
