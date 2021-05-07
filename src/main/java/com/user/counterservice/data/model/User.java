package com.user.counterservice.data.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Mateusz Zyla
 * @since 02.05.2021
 */
@Entity
@Table(name = "`user`")
@Data
public class User {

    @Id
    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "request_count", nullable = false)
    private Long counter;
}
