package com.user.counterservice.service.impl;

import com.user.counterservice.service.RedisSynchronizer;
import com.user.counterservice.message.producer.UserCounterProducer;
import com.user.counterservice.service.UserMessageProcessor;
import com.user.counterservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author Mateusz Zyla
 * @since 04.05.2021
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserMessageProcessorImpl implements UserMessageProcessor {

    private final UserService userService;
    private final RedisSynchronizer redisSynchronizer;
    private final UserCounterProducer userCounterProducer;

    @Override
    public void tryToProcessMessage(String message) {
        if (redisSynchronizer.tryLock(message)) {
            try {
                userService.createOrUpdateUser(message);
            } finally {
                redisSynchronizer.unlock(message);
            }
        } else {
            requeueWithDelay(message);
        }
    }

    private void requeueWithDelay(String content) {
        log.info("Requeue message: login={}", content);
        userCounterProducer.requeueWithDelay(content);
    }
}
