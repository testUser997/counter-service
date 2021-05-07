package com.user.counterservice.service;

import java.time.Duration;

public interface RedisSynchronizer {

    boolean tryLock(String uniqueIdentifier);

    boolean tryLock(String uniqueIdentifier, Duration ttl);

    boolean tryLock(String uniqueIdentifier, Duration ttl, int maxHoldCount);

    void unlock(String uniqueIdentifier);
}
