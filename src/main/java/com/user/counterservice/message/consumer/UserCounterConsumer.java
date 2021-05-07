package com.user.counterservice.message.consumer;

import com.user.counterservice.message.queue.Queues;
import com.user.counterservice.service.UserMessageProcessor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Mateusz Zyla
 * @since 03.05.2021
 */
@Component
@Slf4j
@RequiredArgsConstructor
public class UserCounterConsumer {

    private final UserMessageProcessor userMessageProcessor;

    @RabbitListener(queues = Queues.USER_QUEUE)
    public void onMessage(String content) {
        userMessageProcessor.tryToProcessMessage(content);
    }
}
