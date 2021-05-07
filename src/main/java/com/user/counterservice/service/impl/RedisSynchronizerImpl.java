package com.user.counterservice.service.impl;


import com.user.counterservice.service.RedisSynchronizer;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

@Service
@Data
@Slf4j
@RequiredArgsConstructor
public class RedisSynchronizerImpl implements RedisSynchronizer {

    private final RedissonClient redissonClient;

    @Override

    public boolean tryLock(String uniqueIdentifier) {
        RLock lock = getLockForId(uniqueIdentifier);
        return lock.tryLock();
    }

    @Override
    public boolean tryLock(String uniqueIdentifier, Duration ttl) {
        return tryLock(uniqueIdentifier, ttl, -1);
    }

    @Override
    public boolean tryLock(String uniqueIdentifier, Duration ttl, int maxHoldCount) {
        RLock lock = getLockForId(uniqueIdentifier);
        try {
            boolean locked = lock.tryLock(0L, ttl.toMillis(), TimeUnit.MILLISECONDS)
                        && (maxHoldCount < 0 || lock.getHoldCount() <= maxHoldCount);

            if (log.isDebugEnabled()) {
                log.debug("Lock result='{}', key='{}', holdCount='{}', threadId='{}'", locked, lock.getName(), lock.getHoldCount(), Thread.currentThread().getId());
            }

            return locked;
        } catch (InterruptedException e) {
            log.error("Someone called interrupt !", e);
        }
        return false;
    }

    @Override
    public void unlock(String uniqueIdentifier) {
        RLock lock = getLockForId(uniqueIdentifier);
        lock.unlock();
    }

    private RLock getLockForId(String id) {
        return redissonClient.getLock(id + "lock");
    }
}