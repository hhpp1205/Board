package team.cupid.realworld.domain.follow.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.cupid.realworld.domain.follow.dto.FollowResponse;
import team.cupid.realworld.domain.follow.dto.SimpleFollowResponse;
import team.cupid.realworld.domain.follow.service.FollowService;
import team.cupid.realworld.global.security.principal.CustomUserDetails;

import java.util.List;

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

    @GetMapping("/follow/following")
    public ResponseEntity<List<FollowResponse>> getFollowing(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok().body(followService.getFollowing(customUserDetails.getId()));
    }

    @GetMapping("/follow/follower")
    public ResponseEntity<List<FollowResponse>> getFollower(
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok().body(followService.getFollower(customUserDetails.getId()));
    }

}
