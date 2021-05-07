package com.user.counterservice.message.queue;

import lombok.Getter;

/**
 * @author Mateusz Zyla
 * @since 06.05.2021
 */
@Getter
public final class Queues {
    public static final String USER_QUEUE = "local.inmemory.queue.userCounter";
}
