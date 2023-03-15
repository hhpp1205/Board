package team.cupid.realworld.domain.auth.service;

import org.springframework.stereotype.Service;
import team.cupid.realworld.domain.auth.dto.AccessToken;
import team.cupid.realworld.domain.auth.dto.SignInDto;
import team.cupid.realworld.global.jwt.dto.TokenDto;

@Service
public class AuthService {
    public TokenDto login(SignInDto request) {
        // TODO: 2023/03/14
        return null;
    }

    public String reissue(AccessToken accessToken, String refreshToken) {
        // TODO: 2023/03/14
        return null;
    }
}
