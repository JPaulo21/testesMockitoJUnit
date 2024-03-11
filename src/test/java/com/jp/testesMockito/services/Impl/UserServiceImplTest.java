package com.jp.testesMockito.services.Impl;

import com.jp.testesMockito.domain.User;
import com.jp.testesMockito.domain.dto.UserDTO;
import com.jp.testesMockito.mapper.UserMapper;
import com.jp.testesMockito.repositories.UserRepository;
import com.jp.testesMockito.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID = 1;
    public static final String NAME     = "Joao";
    public static final String EMAIL    = "joao@gmail.com";
    public static final String PASSWORD = "123";
    public static final String USUARIO_NAO_ENCONTRADO = "Usuário não encontrado";

    @InjectMocks // Irá receber as classes Mockadas,
    private UserServiceImpl userService;

    @Mock //Objeto mockado, ou seja será colocado no objeto InjectMock, mas será um objeto simulado, o objeto não irá fazer as funções de fato, serão premeditadas
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    private UserDTO userDTO;
    private User user;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        startUser();
    }


    @Test
    @DisplayName("When findById then return an UserInstance")
    void whenfindById_ThenReturnAnUserInstance() {
        //Mockando o método findById do repository, ao ser chamado pelo service, retornará o objeto optionalUser desta classe de teste
        when(userRepository.findById(Mockito.anyInt())).thenReturn(optionalUser);

        User userResponse = userService.findById(ID);

        assertNotNull(userResponse);
        // assertEquals: 1 - esperado, 2 - variavel
        assertEquals(User.class, userResponse.getClass());
        assertEquals(ID, userResponse.getId());
        assertEquals(NAME, userResponse.getName());
        assertEquals(EMAIL, userResponse.getEmail());
    }

    @Test
    @DisplayName("When findById then return an ObjectNotFoundException")
    void whenFindByIdThenReturnAnObjectNotFoundException(){
       when(userRepository.findById(anyInt()))
               .thenThrow(new ObjectNotFoundException(USUARIO_NAO_ENCONTRADO));

       try {
           userService.findById(ID);
       } catch (Exception ex){
           assertEquals(ObjectNotFoundException.class, ex.getClass());
           assertEquals(USUARIO_NAO_ENCONTRADO, ex.getMessage());
       }
    }

    @Test()
    @DisplayName("When findById then return ObjectNotFoundException")
    void whenFindByIdThenReturnObjectNotFoundException(){
        when(userRepository.findById(anyInt()))
                .thenThrow(new ObjectNotFoundException(USUARIO_NAO_ENCONTRADO));

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> userService.findById(ID));
        assertEquals(ObjectNotFoundException.class, ex.getClass());
        assertEquals(USUARIO_NAO_ENCONTRADO, ex.getMessage());
    }

    @Test
    void whenFindAllthenReturnAnListoUfsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> listUsers = userService.findAll();

        assertNotNull(listUsers);
        assertEquals(1, listUsers.size());
        assertEquals(User.class, listUsers.get(0).getClass());
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
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}