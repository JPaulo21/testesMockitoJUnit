package com.jp.testesMockito.service;

import com.jp.testesMockito.domain.User;

public interface UserService {

    User findById(Integer id);
}
