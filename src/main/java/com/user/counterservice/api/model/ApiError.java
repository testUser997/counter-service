package com.user.counterservice.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mateusz Zyla
 * @since 07.05.2021
 */
@AllArgsConstructor
@Getter
public class ApiError {

    private String message;
}
