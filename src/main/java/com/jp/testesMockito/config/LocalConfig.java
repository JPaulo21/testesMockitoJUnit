package com.jp.testesMockito.config;

import com.jp.testesMockito.domain.User;
import com.jp.testesMockito.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("test")
@RequiredArgsConstructor
public class LocalConfig {

    private final UserRepository userRepository;

    @PostConstruct
    public void startDB(){
        User user1 = new User(null, "Valdir", "valdir@gmail.com", "123");
        User user2 = new User(null, "Maria", "maria@gmail.com", "123");
        User user3 = new User(null, "João", "joao@gmail.com", "123");
        User user4 = new User(null, "Carol", "carol@gmail.com", "123");
        User user5 = new User(null, "Pedro", "pedro@gmail.com", "123");

        userRepository.saveAll(List.of(user1, user2, user3, user4, user5));
    }
}
