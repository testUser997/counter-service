package com.user.counterservice.message.producer;

import com.user.counterservice.message.queue.Queues;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mateusz Zyla
 * @since 03.05.2021
 */
@Service
@RequiredArgsConstructor
public class UserCounterProducer {

    private final RabbitTemplate rabbitTemplate;

    private static final String DELAY_HEADER = "x-delay";

    public void send(String login) {
        rabbitTemplate.convertAndSend(Queues.USER_QUEUE, login);
    }

    public void requeueWithDelay(String login) {
        Map<String, Object> headersMap = new HashMap<>();
        headersMap.put(DELAY_HEADER, 500L);

        rabbitTemplate.convertAndSend(Queues.USER_QUEUE, MessageBuilder.createMessage(login, new MessageHeaders(headersMap)));
    }
}
