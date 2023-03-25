package team.cupid.realworld.domain.auth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team.cupid.realworld.domain.auth.dto.AccessToken;
import team.cupid.realworld.domain.auth.dto.SignInDto;
import team.cupid.realworld.domain.member.domain.repository.MemberRepository;
import team.cupid.realworld.domain.member.exception.MemberNotFoundException;
import team.cupid.realworld.global.error.exception.ErrorCode;
import team.cupid.realworld.global.jwt.TokenProvider;
import team.cupid.realworld.global.jwt.dto.TokenDto;
import team.cupid.realworld.global.jwt.error.TokenNotFoundException;
import team.cupid.realworld.global.security.principal.CustomUserDetails;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder managerBuilder;


    public TokenDto login(SignInDto request) {
        String password = passwordEncoder.encode(request.getPassword());

        CustomUserDetails userDetails = memberRepository.findUserDetailsByEmail(request.getEmail())
                .orElseThrow(() -> new MemberNotFoundException("Member를 찾을 수 없습니다."));

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(userDetails, password);

        Authentication authenticate = managerBuilder.getObject().authenticate(token);

        return tokenProvider.createToken(userDetails.getId(), authenticate);
    }

    public AccessToken reissue(AccessToken accessToken, String refreshToken) {
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new TokenNotFoundException("잘못된 JWT 서명입니다." ,ErrorCode.HANDLE_ACCESS_DENIED);
        }

        Authentication authentication = tokenProvider.getAuthentication(accessToken.getAccessToken());
        CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
        return AccessToken.of(tokenProvider.createToken(principal.getId(), authentication).getAccessToken());
    }
}
