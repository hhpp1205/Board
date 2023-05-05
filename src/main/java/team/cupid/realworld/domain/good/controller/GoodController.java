package team.cupid.realworld.domain.good.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.cupid.realworld.domain.good.dto.GoodStateDto;
import team.cupid.realworld.domain.good.service.GoodService;
import team.cupid.realworld.global.security.principal.CustomUserDetails;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/good")
public class GoodController {

    private final GoodService goodService;

    @PostMapping
    public ResponseEntity<String> updateState(
            @RequestBody @Valid final GoodStateDto request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
            ) {
        return goodService.updateState(request, customUserDetails.getId());
    }
}
