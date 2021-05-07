package com.user.counterservice.service;

import com.user.counterservice.data.model.User;

/**
 * @author Mateusz Zyla
 * @since 02.05.2021
 */
public interface UserService {

    User createOrUpdateUser(String login);
}
