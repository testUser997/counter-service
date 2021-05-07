package com.user.counterservice.api;

import com.user.counterservice.api.model.UserResource;
import com.user.counterservice.client.GitHubClient;
import com.user.counterservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mateusz Zyla
 * @since 02.05.2021
 */
@RestController
@RequestMapping(value = "/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final GitHubClient gitHubClient;
    private final UserMapper userMapper;

    @GetMapping(produces = "application/json", path = "/{user}")
    public ResponseEntity<UserResource> getUserDetails(@PathVariable(name = "user") String user) {
        return new ResponseEntity<>(userMapper.map(gitHubClient.getUserDetails(user.trim())), HttpStatus.OK);
    }
}
