package com.user.counterservice.service.impl;

import com.user.counterservice.data.model.User;
import com.user.counterservice.data.repository.UserRepository;
import com.user.counterservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author Mateusz Zyla
 * @since 02.05.2021
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public User createOrUpdateUser(String login) {
        Optional<User> user = userRepository.findUserByLogin(login);
        User result;

        if (user.isPresent()) {
            user.get().setCounter(user.get().getCounter() + 1);
            result = userRepository.save(user.get());
        } else {
            result = createNewUser(login);
        }

        log.info("Login={} was requested {} times", login, result.getCounter());

        return result;
    }

    private User createNewUser(String login) {
        User user = new User();
        user.setLogin(login);
        user.setCounter(1L);

        return userRepository.save(user);
    }
}