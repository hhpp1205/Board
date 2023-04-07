package team.cupid.realworld.domain.follow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import team.cupid.realworld.domain.follow.domain.Follow;
import team.cupid.realworld.domain.follow.domain.repository.FollowRepository;
import team.cupid.realworld.domain.follow.dto.FollowResponse;
import team.cupid.realworld.domain.follow.exception.DuplicateFollowerError;
import team.cupid.realworld.domain.member.domain.Member;
import team.cupid.realworld.domain.member.domain.repository.MemberRepository;
import team.cupid.realworld.domain.member.exception.MemberNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public Long following(Long fromMemberId, Long toMemberId) {

        Member fromMember = memberRepository.findById(fromMemberId)
                .orElseThrow(() -> new MemberNotFoundException("Member를 찾을 수 없습니다."));

        Member toMember = memberRepository.findById(toMemberId)
                .orElseThrow(() -> new MemberNotFoundException("Member를 찾을 수 없습니다."));

        exitFollow(fromMember, toMember);

        Follow follow = Follow.of(fromMember, toMember);

        return followRepository.save(follow).getFollowId();
    }

    public void unFollowing(Long followingId, Long followerId) {
        // TODO: 2023/04/06
    }

    public ResponseEntity<List<FollowResponse>> getFollowing(Long memberId) {
        // TODO: 2023/04/06
        return null;
    }

    public ResponseEntity<List<FollowResponse>> getFollower(Long memberId) {
        // TODO: 2023/04/06
        return null;
    }

    private void exitFollow(Member fromMember, Member toMember) {
        if(followRepository.existsByFromMemberAndToMember(fromMember, toMember)){
            throw new DuplicateFollowerError();
        }
    }
}
