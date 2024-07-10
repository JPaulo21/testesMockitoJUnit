package com.jp.testesMockito.controller;

import com.jp.testesMockito.domain.User;
import com.jp.testesMockito.domain.dto.UserDTO;
import com.jp.testesMockito.mapper.UserMapper;
import com.jp.testesMockito.services.Impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@DisplayName("Tests in the layer UserController")
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

    @Mock
    private UriComponentsBuilder ucb;

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
    void whenFindByAllThenReturnAListOfUserDTO() {
        when(userService.findAll()).thenReturn(List.of(user));
        when(userMapper.toDTO(any())).thenReturn(userDTO);

        ResponseEntity<List<UserDTO>> listUsersResponse = userController.findByAll();

        assertNotNull(listUsersResponse);
        assertNotNull(listUsersResponse.getBody());
        assertEquals(HttpStatus.OK, listUsersResponse.getStatusCode());
        assertEquals(1, listUsersResponse.getBody().size());
        assertEquals(ArrayList.class, listUsersResponse.getBody().getClass());
        assertEquals(UserDTO.class, listUsersResponse.getBody().get(0).getClass());
        assertEquals(ID, listUsersResponse.getBody().get(0).id());
    }

    @Test
    void whenCreateThenReturnCreated() {
        URI location = URI.create("/api/v1/users/1");
        when(userMapper.toUser(any())).thenReturn(user);
        when(userService.create(any(User.class))).thenReturn(user);
        when(ucb.path(anyString())).thenReturn(ucb);
        when(ucb.buildAndExpand(user.getId()))
                .thenReturn(UriComponentsBuilder.fromUri(location).build());

        ResponseEntity<Void> userResponse = userController.create(userDTO, ucb);

        assertEquals(HttpStatus.CREATED, userResponse.getStatusCode());
        assertNotNull(userResponse.getHeaders().getLocation());
    }

    @Test
    void whenUpdateUserWithSucess() {
        when(userService.update(anyInt(), any(UserDTO.class))).thenReturn(user);
        when(userMapper.toDTO(any(User.class))).thenReturn(userDTO);

        ResponseEntity<UserDTO> userDTOResponse = userController.update(ID, userDTO);

        assertNotNull(userDTOResponse);
        assertNotNull(userDTOResponse.getBody());
        assertEquals(HttpStatus.OK, userDTOResponse.getStatusCode());
        assertEquals(UserDTO.class, userDTOResponse.getBody().getClass());
    }

    @Test
    void whenDeleteThenReturnSucess() {
        doNothing().when(userService).delete(anyInt());

        ResponseEntity<Void> userDTOResponse = userController.delete(ID);

        assertNotNull(userDTOResponse);
        assertEquals(HttpStatus.NO_CONTENT, userDTOResponse.getStatusCode());
        verify(userService, times(1)).delete(anyInt());
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
    }
}