package com.example.ewdj_ep3.mapper;

import com.example.ewdj_ep3.domain.user.User;
import com.example.ewdj_ep3.dto.request.UserInputDTO;

public class UserMapper {

    private UserMapper() {
    }

    public static User toEntity(UserInputDTO dto) {

        if (dto == null) {
            return null;
        }

        return User.builder()
                .name(dto.name())
                .lastname(dto.lastname())
                .email(dto.email())
                .passwordHash(dto.password())
                .build();
    }
}