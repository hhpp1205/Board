package team.cupid.realworld.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import team.cupid.realworld.global.jwt.dto.TokenDto;
import team.cupid.realworld.global.security.principal.CustomUserDetailsService;

import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
@Slf4j
@Component
public class TokenProvider implements InitializingBean {

    private static final String AUTHORITIES_KEY = "role";

    private final String secret;
    private final long accessTokenValidityInMillisecond;
    private final long refreshTokenValidityInMillisecond;
    private final CustomUserDetailsService customUserDetailsService;

    private Key key;

    public TokenProvider(@Value("${jwt.secret}") String secret,
                         @Value("${jwt.accessToken-validity-in-seconds}") long accessTokenValidityInMillisecond,
                         @Value("${jwt.refreshToken-validity-in-seconds}")long refreshTokenValidityInMillisecond,
                         CustomUserDetailsService customUserDetailsService) {
        this.secret = secret;
        this.accessTokenValidityInMillisecond = accessTokenValidityInMillisecond;
        this.refreshTokenValidityInMillisecond = refreshTokenValidityInMillisecond;
        this.customUserDetailsService = customUserDetailsService;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto createToken(Long memberId, Authentication authentication) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();

        String accessToken = Jwts.builder()
                .claim("id", memberId.toString())
                .claim(AUTHORITIES_KEY, authorities)
                .setExpiration(new Date(now + accessTokenValidityInMillisecond))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        String refreshToken = Jwts.builder()
                .claim("id", memberId.toString())
                .setExpiration(new Date(now + refreshTokenValidityInMillisecond))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        return TokenDto.of(accessToken, refreshToken);
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts
                .parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        List<SimpleGrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        String id = String.valueOf(claims.get("id"));

        UserDetails principal = customUserDetailsService.loadUserByUsername(id);

        return new UsernamePasswordAuthenticationToken(principal, principal.getPassword(), authorities);
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다.");
        }catch (ExpiredJwtException e) {
            log.error("만료된 JWT 토큰입니다.");
        }catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 JWT 토큰입니다.");
        }catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }


}
