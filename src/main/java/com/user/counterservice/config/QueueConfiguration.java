package com.user.counterservice.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author Mateusz Zyla
 * @since 03.05.2021
 */
@Configuration
@EnableRabbit
@EnableBinding
public class QueueConfiguration {

    @Autowired
    private ConnectionFactory rabbitConnectionFactory;

    @Value("${queues.config.user.name:user.queue.userCounter}")
    private String userQueue;

    @Value("${queues.config.user.exchange:user.queue.userCounterExchange}")
    private String userQueueExchange;

    @Value("${queues.config.user.key:user.queue.userCounterKey}")
    private String userQueueKey;


    @Bean
    DirectExchange initiateLoanApplicationExchange() {
        return new DirectExchange(userQueueExchange);
    }

    @Bean
    public Queue initiateLoanApplicationQueue() {
        return new Queue(userQueue);
    }

    @Bean
    Binding initiateLoanApplicationBinding(DirectExchange userQueueExchange, Queue userQueue) {
        return BindingBuilder.bind(userQueue).to(userQueueExchange).with(userQueueKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate();
        rabbitTemplate.setConnectionFactory(rabbitConnectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setConnectionFactory(rabbitConnectionFactory);
        factory.setConcurrentConsumers(1);
        factory.setMaxConcurrentConsumers(5);
        return factory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(rabbitConnectionFactory);
    }
}
