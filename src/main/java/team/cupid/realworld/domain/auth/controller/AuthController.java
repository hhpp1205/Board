package team.cupid.realworld.domain.auth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.cupid.realworld.domain.auth.dto.SignInDto;
import team.cupid.realworld.domain.auth.service.AuthService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("public/auth")
    @ResponseStatus(HttpStatus.CREATED)
    public void login(@Validated @RequestBody SignInDto request) {
        // TODO: 2023/03/13
    }

    @DeleteMapping("auth")
    public void logout() {
        // TODO: 2023/03/13
    }

    @PostMapping("auth/reissue")
    public void reissue() {
        // TODO: 2023/03/13  
    }

    @GetMapping("auth/email-check/{email}")
    public boolean emailDuplicateCheck(@PathVariable String email) {
        return authService.emailDuplicateCheck(email);
    }

    @GetMapping("auth/nickname-check/{nickname}")
    public boolean nicknameDuplicateCheck(@PathVariable String nickname) {
        return authService.nicknameDuplicateCheck(nickname);
    }

}
