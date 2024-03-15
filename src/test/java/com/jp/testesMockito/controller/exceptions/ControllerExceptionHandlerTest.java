package com.jp.testesMockito.controller.exceptions;

import com.jp.testesMockito.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

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
    void whenObjectNotFoundExceptionThenReturnAResponseEtity() {
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
    }
}