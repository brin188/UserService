package com.example.userservice.dtos;

import com.example.userservice.models.Role;
import com.example.userservice.models.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserResponseDto {
    private String name;
    private String email;
    private List<Role> roleList;

    public static UserResponseDto fromUser(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        if (user != null) {
            userResponseDto.setName(user.getName());
            userResponseDto.setEmail(user.getEmail());
            userResponseDto.setRoleList(user.getRoles());
        }
        return userResponseDto;
    }
}
