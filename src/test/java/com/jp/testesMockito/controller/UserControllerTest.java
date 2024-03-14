package com.jp.testesMockito.controller;

import com.jp.testesMockito.domain.User;
import com.jp.testesMockito.domain.dto.UserDTO;
import com.jp.testesMockito.mapper.UserMapper;
import com.jp.testesMockito.services.Impl.UserServiceImpl;
import com.jp.testesMockito.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserControllerTest {

    public static final Integer ID      = 1;
    public static final String NAME     = "Joao";
    public static final String EMAIL    = "joao@gmail.com";
    public static final String PASSWORD = "123";

    @InjectMocks
    private UserController userController;

    @Mock
    private UserServiceImpl userService;

    @Mock
    private UserMapper userMapper;

    private UserDTO userDTO;
    private User user;

    @BeforeEach
    private void setUp() {
        startUser();
    }

    @Test
    void whenFindByIdThenReturnSucess() {
        when(userService.findById(anyInt())).thenReturn(user);
        when(userMapper.toDTO(any())).thenReturn(userDTO);

        ResponseEntity<UserDTO> userResponse = userController.findById(ID);

        assertNotNull(userResponse);
        assertNotNull(userResponse.getBody());
        assertEquals(ResponseEntity.class, userResponse.getClass());
        assertEquals(UserDTO.class, userResponse.getBody().getClass());
        assertEquals(ID, userResponse.getBody().id());
        assertEquals(NAME, userResponse.getBody().name());
        assertEquals(EMAIL, userResponse.getBody().email());
        assertEquals(PASSWORD, userResponse.getBody().password());
    }

    @Test
    void findByAll() {
    }

    @Test
    void create() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}