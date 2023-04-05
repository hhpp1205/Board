package team.cupid.realworld.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.cupid.realworld.domain.member.dto.MemberResponse;
import team.cupid.realworld.domain.member.dto.MemberUpdateRequest;
import team.cupid.realworld.domain.member.dto.SignUpRequest;
import team.cupid.realworld.domain.member.dto.SimpleMemberResponse;
import team.cupid.realworld.domain.member.service.MemberService;
import team.cupid.realworld.global.security.principal.CustomUserDetails;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {


    private final MemberService memberService;

    @PostMapping("/public/members")
    public ResponseEntity<SimpleMemberResponse> create(@Validated @RequestBody SignUpRequest request) {
        Long memberId = memberService.create(request.toEntity());
        return ResponseEntity.status(HttpStatus.CREATED).body(new SimpleMemberResponse(memberId));
    }

    @GetMapping("/members/{nickname}")
    public ResponseEntity<MemberResponse> findByNickname(@PathVariable String nickname) {
        return ResponseEntity.ok(memberService.findByNickname(nickname));
    }

    @PutMapping("/members")
    public ResponseEntity<SimpleMemberResponse> update(@Validated @RequestBody MemberUpdateRequest request,
                                                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        Long memberId = memberService.update(customUserDetails.getId(), request.toEntity());
        return ResponseEntity.ok().body(new SimpleMemberResponse(memberId));
    }

    @DeleteMapping("/members")
    public ResponseEntity<Void> delete(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        memberService.delete(customUserDetails.getId());
        return ResponseEntity.ok().build();
    }

    @PostMapping("/public/members/email-confirmation/{email}")
    public ResponseEntity<Void> emailDuplicateCheck(@PathVariable String email) {
        memberService.emailDuplicateCheck(email);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/public/members/nickname-confirmation/{nickname}")
    public ResponseEntity<Void> nicknameDuplicateCheck(@PathVariable String nickname) {
        memberService.nicknameDuplicateCheck(nickname);
        return ResponseEntity.ok().build();
    }

}
