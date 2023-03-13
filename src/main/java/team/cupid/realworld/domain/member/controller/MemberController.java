package team.cupid.realworld.domain.member.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.cupid.realworld.domain.member.dto.MemberResponse;
import team.cupid.realworld.domain.member.dto.MemberUpdateRequest;
import team.cupid.realworld.domain.member.dto.SignUpRequest;
import team.cupid.realworld.domain.member.service.MemberService;
import team.cupid.realworld.global.security.principal.CustomUserDetails;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("public/members")
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@Validated @RequestBody SignUpRequest request) {
        memberService.create(request);
    }

    @GetMapping("member/profile")
    public MemberResponse getMember(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return memberService.findById(customUserDetails.getId());
    }

    @GetMapping("public/members")
    public List<MemberResponse> getMembers() {
        return memberService.findAll();
    }

    @PutMapping("members")
    public void update(@Validated @RequestBody MemberUpdateRequest request,
                       @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        memberService.update(customUserDetails.getId(),request);
    }

    @DeleteMapping("members")
    public void delete(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        memberService.delete(customUserDetails.getId());
    }

}
