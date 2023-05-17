package team.cupid.realworld.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cupid.realworld.domain.follow.domain.Follow;
import team.cupid.realworld.domain.follow.domain.repository.FollowRepository;
import team.cupid.realworld.domain.follow.dto.FollowResponse;
import team.cupid.realworld.domain.follow.exception.DuplicateFollowerError;
import team.cupid.realworld.domain.member.domain.Member;
import team.cupid.realworld.domain.member.domain.repository.MemberRepository;
import team.cupid.realworld.domain.member.exception.MemberNotFoundException;
import team.cupid.realworld.global.common.CustomPageResponse;
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
    @Transactional(readOnly = true)
    public CustomPageResponse<FollowResponse> getFollowing(Long memberId, Pageable pageable) {
        Member fromMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        List<FollowResponse> followResponses = followRepository.findByFromMember(fromMember, pageable).stream()
                .map(follow -> new FollowResponse(
                        follow.getToMember().getNickname(),
                        follow.getToMember().getImage(),
                        follow.getCreatedBy(),
                        follow.getLastModifiedBy()))
                .collect(Collectors.toList());

        if(followResponses.isEmpty()) {
            return CustomPageResponse.of(Page.empty(pageable));
        }

        Page<FollowResponse> page = PageableExecutionUtils.getPage(followResponses, pageable, followRepository.totalCount());
        return CustomPageResponse.of(page);
    }
    
    /**
     * 사용자를 팔로우 한 사람들의 정보
     */
    @Transactional(readOnly = true)
    public CustomPageResponse<FollowResponse> getFollower(Long memberId, Pageable pageable) {
        Member toMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        List<FollowResponse> followResponses = followRepository.findByToMember(toMember, pageable).stream()
                .map(follow -> new FollowResponse(
                        follow.getFromMember().getNickname(),
                        follow.getFromMember().getImage(),
                        follow.getCreatedBy(),
                        follow.getLastModifiedBy()))
                .collect(Collectors.toList());

        if(followResponses.isEmpty()) {
            return CustomPageResponse.of(Page.empty(pageable));
        }

        Page<FollowResponse> page = PageableExecutionUtils.getPage(followResponses, pageable, followRepository.totalCount());
        return CustomPageResponse.of(page);
    }

    private void exitFollow(Member fromMember, Member toMember) {
        if(followRepository.existsByFromMemberAndToMember(fromMember, toMember)){
            throw new DuplicateFollowerError();
        }
    }

}
