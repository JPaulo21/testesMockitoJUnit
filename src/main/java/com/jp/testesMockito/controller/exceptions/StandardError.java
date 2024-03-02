package com.jp.testesMockito.controller.exceptions;

import java.time.LocalDateTime;

public record StandardError(
     LocalDateTime timestamp,
     Integer status,
     String error,
     String path
) {
}
