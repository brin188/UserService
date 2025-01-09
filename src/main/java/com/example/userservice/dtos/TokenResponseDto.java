package com.example.userservice.dtos;

import com.example.userservice.models.Token;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenResponseDto {
    private String token;

    public static TokenResponseDto fromToken(Token token) {
        TokenResponseDto dto = new TokenResponseDto();
        if (token != null) {
            dto.setToken(token.getValue());
        }
        return dto;
    }
}
