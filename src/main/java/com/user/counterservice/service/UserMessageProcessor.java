package com.user.counterservice.service;

/**
 * @author Mateusz Zyla
 * @since 04.05.2021
 */
public interface UserMessageProcessor {

    void tryToProcessMessage(String message);
}
