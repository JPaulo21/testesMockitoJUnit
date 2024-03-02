package com.jp.testesMockito.mapper;

import com.jp.testesMockito.domain.User;
import com.jp.testesMockito.domain.dto.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {



    UserDTO toDTO(User user);

    User toUser(UserDTO userDTO);
}
