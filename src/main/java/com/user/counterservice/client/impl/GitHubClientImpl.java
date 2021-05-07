package com.user.counterservice.client.impl;

import com.user.counterservice.client.GitHubClient;
import com.user.counterservice.client.model.UserDetails;
import com.user.counterservice.message.producer.UserCounterProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


/**
 * @author Mateusz Zyla
 * @since 02.05.2021
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GitHubClientImpl implements GitHubClient {

    private final RestTemplate restTemplate;

    private final UserCounterProducer userCounterProducer;

    @Value("${github.url:test}")
    private String endpointUri;

    @Override
    @Retryable(
            value = {HttpServerErrorException.class},
            backoff = @Backoff(delay = 100, multiplier = 3))
    public UserDetails getUserDetails(String login) {
        ResponseEntity<UserDetails> user;

        try {
            user = restTemplate.getForEntity(UriComponentsBuilder.fromHttpUrl(endpointUri).pathSegment(login).toUriString(),
                    UserDetails.class);
        } catch (Exception ex) {
            log.warn("Cannot retrieve user details from github using login={}", login, ex);
            throw ex;
        }

        //count only successful attempts
        userCounterProducer.send(login);

        return user.getBody();
    }
}
