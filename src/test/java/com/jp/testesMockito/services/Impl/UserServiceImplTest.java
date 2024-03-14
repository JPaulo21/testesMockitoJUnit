package com.jp.testesMockito.services.Impl;

import com.jp.testesMockito.domain.User;
import com.jp.testesMockito.domain.dto.UserDTO;
import com.jp.testesMockito.mapper.UserMapper;
import com.jp.testesMockito.repositories.UserRepository;
import com.jp.testesMockito.services.exceptions.DataIntegratyViolationException;
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
@DisplayName("Test in the service layer of entity users")
class UserServiceImplTest {

    public static final Integer ID      = 1;
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
    void testFindUserByIdNotFound() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> {
            userService.findById(ID);
        });
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
    void whenCreateThenReturnSucess() {
        when(userRepository.save(any(User.class))).thenReturn(user);

        User userResponse = userService.create(new User());

        assertSame(user, userResponse);

        assertNotNull(userResponse);
        assertEquals(User.class, userResponse.getClass());
        assertEquals(ID, userResponse.getId());
        assertEquals(NAME, userResponse.getName());
        assertEquals(EMAIL, userResponse.getEmail());
    }

    @Test
    @DisplayName("When Create User with e-mail already registered")
    void whenCreateThenReturnDataIntegratyViolationException(){
        when(userRepository.findByEmail(anyString())).thenThrow(new DataIntegratyViolationException("Email já cadastrado"));

        DataIntegratyViolationException ex = assertThrows(DataIntegratyViolationException.class, () -> userService.create(user));

        assertNotNull(ex);
        assertEquals(DataIntegratyViolationException.class, ex.getClass());
        assertEquals(ex.getMessage(), "Email já cadastrado");
    }

    @Test
    @DisplayName("When creating a user check if there is another user with the same email throw exception")
    void WhenCreateUserCheckIfThereIsAnotherUserWithTheSameEmailThrowException(){
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);

        DataIntegratyViolationException ex = assertThrows(DataIntegratyViolationException.class, () -> userService.create(user));

        assertNotNull(ex);
        assertEquals(DataIntegratyViolationException.class, ex.getClass());
        assertEquals(ex.getMessage(), "Email já cadastrado");
    }

    @Test
    @DisplayName("When Create User with e-mail already registered - 3")
    void whenCreateThenReturnDataIntegratyViolationException3(){
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);

        try {
            userService.create(user);
        } catch (DataIntegratyViolationException ex) {
            assertNotNull(ex);
            assertEquals(DataIntegratyViolationException.class, ex.getClass());
            assertEquals(ex.getMessage(), "Email já cadastrado");
        }
    }

    @Test
    void updateUser() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);

        User userResponse = userService.update(ID, userDTO);

        assertNotNull(userResponse);
        assertEquals(User.class, userResponse.getClass());
        assertEquals(ID, userResponse.getId());
        assertEquals(NAME, userResponse.getName());
        assertEquals(EMAIL, userResponse.getEmail());
    }

    @Test
    @DisplayName("When update a user check if there is another user with the same email throw exception")
    void WhenUpdateUserCheckIfThereIsAnotherUserWithTheSameEmailThrowException(){
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);

        DataIntegratyViolationException ex = assertThrows(DataIntegratyViolationException.class, () -> userService.update(ID, userDTO));

        assertNotNull(ex);
        assertEquals(DataIntegratyViolationException.class, ex.getClass());
        assertEquals(ex.getMessage(), "Email já cadastrado");
    }

    @Test
    @DisplayName("When update user, but there isn't user with ID")
    void WhenUpdateUserButThereIsNotUserWithID(){
        when(userRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException(USUARIO_NAO_ENCONTRADO));

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> userService.update(ID, userDTO));

        assertNotNull(ex);
        assertEquals(ObjectNotFoundException.class, ex.getClass());
        assertEquals(USUARIO_NAO_ENCONTRADO, ex.getMessage());
    }

    @Test
    void deleteUserWithSucess() {
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);
        doNothing().when(userRepository).deleteById(anyInt());

        userService.delete(ID);

        verify(userRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void deleteUserThrowObjectNotFoundException() {
        when(userRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException(USUARIO_NAO_ENCONTRADO));

        ObjectNotFoundException ex = assertThrows(ObjectNotFoundException.class, () -> userService.delete(ID));

        assertEquals(ObjectNotFoundException.class, ex.getClass());
        assertEquals(USUARIO_NAO_ENCONTRADO, ex.getMessage());
    }

    private void startUser(){
        user = new User(ID, NAME, EMAIL, PASSWORD);
        userDTO = new UserDTO(ID, NAME, EMAIL, PASSWORD);
        optionalUser = Optional.of(new User(ID, NAME, EMAIL, PASSWORD));
    }
}