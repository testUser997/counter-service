package com.user.counterservice.data.repository;

import com.user.counterservice.data.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.RepositoryDefinition;

import java.util.Optional;

/**
 * @author Mateusz Zyla
 * @since 02.05.2021
 */
@RepositoryDefinition(domainClass = User.class, idClass = String.class)
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findUserByLogin(String login);
}
