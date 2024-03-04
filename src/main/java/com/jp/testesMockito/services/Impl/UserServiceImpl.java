package com.jp.testesMockito.services.Impl;

import com.jp.testesMockito.domain.User;
import com.jp.testesMockito.domain.dto.UserDTO;
import com.jp.testesMockito.repositories.UserRepository;
import com.jp.testesMockito.services.UserService;
import com.jp.testesMockito.services.exceptions.DataIntegratyViolationException;
import com.jp.testesMockito.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> new ObjectNotFoundException("Usuário não encontrado")
        );
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User create(User user) {
        findByEmail(user.getEmail());
        return userRepository.save(user);
    }

    @Override
    public User update(Integer id, UserDTO userDTO) {
        User user = findById(id);
        findByEmail(userDTO.email());
        user.setName(userDTO.name() == null ? user.getName() : userDTO.name());
        user.setEmail(userDTO.email() == null ? user.getEmail() : userDTO.email());
        user.setPassword(userDTO.password() == null ? user.getPassword() : userDTO.password());
        return userRepository.save(user);
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        userRepository.deleteById(id);
    }

    private void findByEmail(String email){
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()){
            throw new DataIntegratyViolationException("Email já cadastrado");
        }
    }

}
