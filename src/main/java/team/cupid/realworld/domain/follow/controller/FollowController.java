package team.cupid.realworld.domain.follow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.cupid.realworld.domain.follow.dto.SimpleFollowResponse;
import team.cupid.realworld.domain.follow.service.FollowService;
import team.cupid.realworld.global.security.principal.CustomUserDetails;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow/{followerId}")
    public ResponseEntity<SimpleFollowResponse> following(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                       @PathVariable Long followerId) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new SimpleFollowResponse(followService.following(customUserDetails.getId(), followerId)));
    }

    @DeleteMapping("/follow/{followerId}")
    public ResponseEntity<Void> unFollowing(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                            @PathVariable Long followerId) {
        followService.unFollowing(customUserDetails.getId(), followerId);
        return ResponseEntity.ok().build();
    }

}
