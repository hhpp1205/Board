package team.cupid.realworld.global.jwt.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TokenDto {

    private String accessToken;

    private String refreshToken;

    public static TokenDto of(String accessToken, String refreshToken) {
        return new TokenDto(accessToken, refreshToken);
    }
}
