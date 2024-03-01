package com.jp.testesMockito.service.Impl;

import com.jp.testesMockito.domain.User;
import com.jp.testesMockito.repositories.UserRepository;
import com.jp.testesMockito.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findById(Integer id) {
        return userRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Usuário não encontrado")
        );
    }


}
