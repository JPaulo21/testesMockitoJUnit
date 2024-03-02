package com.jp.testesMockito.service.Impl;

import com.jp.testesMockito.domain.User;
import com.jp.testesMockito.repositories.UserRepository;
import com.jp.testesMockito.service.UserService;
import com.jp.testesMockito.service.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

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

}
