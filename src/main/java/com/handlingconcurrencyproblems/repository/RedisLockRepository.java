package com.handlingconcurrencyproblems.repository;

import java.time.Duration;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RedisLockRepository {

    private final RedisTemplate<String, String> redisTemplate;


    public boolean lock(long key) {
        return redisTemplate.opsForValue()
                .setIfAbsent(generateKey(key), "lock", Duration.ofMillis(3_000));
    }

    public boolean unlock(long key) {
        return redisTemplate.delete(generateKey(key));
    }

    private String generateKey(long key) {
        return String.valueOf(key);
    }
}
