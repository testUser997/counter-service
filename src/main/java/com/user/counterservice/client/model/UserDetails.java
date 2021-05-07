package com.user.counterservice.client.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author Mateusz Zyla
 * @since 02.05.2021
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Getter
@Setter
public class UserDetails {

    private Long id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private Date createdAt;
    private Long followers;
    private Long publicRepos;
}
