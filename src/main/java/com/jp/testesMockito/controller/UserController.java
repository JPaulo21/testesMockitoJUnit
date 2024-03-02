package com.jp.testesMockito.controller;

import com.jp.testesMockito.domain.User;
import com.jp.testesMockito.domain.dto.UserDTO;
import com.jp.testesMockito.mapper.UserMapper;
import com.jp.testesMockito.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Integer id){
        User user = userService.findById(id);
        return ResponseEntity.ok().body(userMapper.toDTO(user));
    }
}
