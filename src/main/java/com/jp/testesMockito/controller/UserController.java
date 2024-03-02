package com.jp.testesMockito.controller;

import com.jp.testesMockito.domain.User;
import com.jp.testesMockito.domain.dto.UserDTO;
import com.jp.testesMockito.mapper.UserMapper;
import com.jp.testesMockito.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<List<UserDTO>> findByAll(){
        return ResponseEntity.ok(userService.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody UserDTO userDTO, UriComponentsBuilder ucb){
        User user = userService.create(userMapper.toUser(userDTO));
        URI location = ucb
                .path("/api/v1/users/{id}")
                .buildAndExpand(user.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Integer id, @RequestBody UserDTO userDTO){
        User user = userService.update(id, userDTO);
        return ResponseEntity.ok(userMapper.toDTO(user));
    }

}
