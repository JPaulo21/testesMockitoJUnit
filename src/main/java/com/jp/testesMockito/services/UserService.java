package com.jp.testesMockito.services;

import com.jp.testesMockito.domain.User;

import java.util.List;

public interface UserService {

    User findById(Integer id);
    List<User> findAll();
    User create(User user);
}
