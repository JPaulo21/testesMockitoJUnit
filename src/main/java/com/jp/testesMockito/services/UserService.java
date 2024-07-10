package com.jp.testesMockito.services;

import com.jp.testesMockito.domain.User;
import com.jp.testesMockito.domain.dto.UserDTO;

import java.util.List;

public interface UserService {

    User findById(Integer id);
    List<User> findAll();
    User create(User user);
    User update(Integer id, UserDTO userDTO);
    void delete(Integer id);
}
