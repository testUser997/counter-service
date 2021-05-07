package com.user.counterservice.client;

import com.user.counterservice.client.model.UserDetails;

/**
 * @author Mateusz Zyla
 * @since 02.05.2021
 */
public interface GitHubClient {

    UserDetails getUserDetails(String login);
}
