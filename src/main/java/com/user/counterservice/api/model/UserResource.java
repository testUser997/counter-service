package com.user.counterservice.api.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Mateusz Zyla
 * @since 02.05.2021
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Data
public class UserResource {

    private Long id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
    private BigDecimal calculations;
}
