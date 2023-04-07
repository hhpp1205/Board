package team.cupid.realworld.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cupid.realworld.domain.follow.domain.Follow;
import team.cupid.realworld.domain.follow.domain.repository.FollowRepository;
import team.cupid.realworld.domain.follow.dto.FollowResponse;
import team.cupid.realworld.domain.follow.exception.DuplicateFollowerError;
import team.cupid.realworld.domain.member.domain.Member;
import team.cupid.realworld.domain.member.domain.repository.MemberRepository;
import team.cupid.realworld.domain.member.exception.MemberNotFoundException;
import team.cupid.realworld.global.error.exception.ErrorCode;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public Long following(Long fromMemberId, Long toMemberId) {

        Member fromMember = memberRepository.findById(fromMemberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        Member toMember = memberRepository.findById(toMemberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        exitFollow(fromMember, toMember);

        Follow follow = Follow.of(fromMember, toMember);

        return followRepository.save(follow).getFollowId();
    }

    public void unFollowing(Long fromMemberId, Long toMemberId) {
        Member fromMember = memberRepository.findById(fromMemberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        Member toMember = memberRepository.findById(toMemberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        followRepository.deleteByFromMemberAndToMember(fromMember, toMember);
    }

    /**
     * 사용자가 팔로우 한 사람들의 정보
     */
    public List<FollowResponse> getFollowing(Long memberId) {
        Member fromMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        return followRepository.findByFromMember(fromMember).stream()
                .map(follow -> new FollowResponse(follow.getToMember().getNickname(), follow.getToMember().getImage()))
                .collect(Collectors.toList());
    }
    
    /**
     * 사용자를 팔로우 한 사람들의 정보
     */
    public List<FollowResponse> getFollower(Long memberId) {
        Member toMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        return followRepository.findByToMember(toMember).stream()
                .map(follow -> new FollowResponse(follow.getFromMember().getNickname(), follow.getFromMember().getImage()))
                .collect(Collectors.toList());
    }

    private void exitFollow(Member fromMember, Member toMember) {
        if(followRepository.existsByFromMemberAndToMember(fromMember, toMember)){
            throw new DuplicateFollowerError(ErrorCode.DUPLICATE_FOLLOW);
        }
    }
}
