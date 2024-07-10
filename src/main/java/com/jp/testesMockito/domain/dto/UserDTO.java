package com.jp.testesMockito.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDTO(
        Integer id,
        String name,
        String email,
        @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
        String password
) { }
