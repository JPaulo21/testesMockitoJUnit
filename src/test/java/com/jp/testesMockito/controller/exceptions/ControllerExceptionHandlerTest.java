package com.jp.testesMockito.controller.exceptions;

import com.jp.testesMockito.services.exceptions.DataIntegratyViolationException;
import com.jp.testesMockito.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import com.jp.testesMockito.controller.exceptions.StandardError;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ControllerExceptionHandlerTest {


    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";

    @InjectMocks
    private ControllerExceptionHandler exceptionHandler;


    @BeforeEach
    void setUp() {
    }

    @Test
    void whenObjectNotFoundExceptionThenReturnAResponseEntity() {
        ResponseEntity<StandardError> exceptionResponse = exceptionHandler
                .objectNotFound(
                        new ObjectNotFoundException(USUARIO_NAO_ENCONTRADO),
                        new MockHttpServletRequest());

        assertNotNull(exceptionResponse);
        assertNotNull(exceptionResponse.getBody());
        assertEquals(HttpStatus.NOT_FOUND, exceptionResponse.getStatusCode());
        assertEquals(StandardError.class, exceptionResponse.getBody().getClass());
        assertEquals(USUARIO_NAO_ENCONTRADO, exceptionResponse.getBody().error());
        assertEquals(404, exceptionResponse.getBody().status());
    }

    @Test
    void dataIntegratyViolationException() {
        ResponseEntity<StandardError> exceptionResponse = exceptionHandler
                .dataIntegratyViolationException(
                        new DataIntegratyViolationException("Email já cadastrado"),
                        new MockHttpServletRequest());

        assertNotNull(exceptionResponse);
        assertNotNull(exceptionResponse.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, exceptionResponse.getStatusCode());
        assertEquals(StandardError.class, exceptionResponse.getBody().getClass());
        assertEquals("Email já cadastrado", exceptionResponse.getBody().error());
        assertEquals(400, exceptionResponse.getBody().status());
        assertNotEquals(LocalDateTime.now(), exceptionResponse.getBody().timestamp());
        assertNotEquals("/users/2", exceptionResponse.getBody().path());
    }
}