package com.jp.testesMockito.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public record UserDTO(
        Integer id,
        String name,
        String email,
        @JsonIgnore
        String password
) { }
