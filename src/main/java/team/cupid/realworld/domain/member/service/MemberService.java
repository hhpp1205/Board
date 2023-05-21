package team.cupid.realworld.domain.member.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cupid.realworld.domain.member.domain.Member;
import team.cupid.realworld.domain.member.domain.repository.MemberRepository;
import team.cupid.realworld.domain.member.dto.MemberResponse;
import team.cupid.realworld.domain.member.exception.DuplicateEmailException;
import team.cupid.realworld.domain.member.exception.DuplicateNicknameException;
import team.cupid.realworld.domain.member.exception.MemberNotFoundException;
import team.cupid.realworld.global.error.exception.ErrorCode;
import team.cupid.realworld.global.policy.RedisPolicy;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Long create(Member member) {
        emailDuplicateCheck(member.getNickname());
        nicknameDuplicateCheck(member.getNickname());

        member.setEncodePassword(passwordEncoder.encode(member.getPassword()));

        return memberRepository.save(member).getMemberId();
    }

    @CachePut(key = "#memberId", value = RedisPolicy.MEMBER_KEY)
    public MemberResponse update(Long memberId, Member member) {
        log.info("Call update");
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        if (!findMember.getNickname().equals(member.getNickname())) {
            nicknameDuplicateCheck(member.getNickname());
        }

        findMember.update(member);
        return MemberResponse.of(findMember);
    }

    public void delete(Long memberId) {
        memberRepository.deleteById(memberId);
    }


    @Transactional(readOnly = true)
    public MemberResponse findByNickname(String nickname) {
        return MemberResponse.of(memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND)));
    }

    @Transactional(readOnly = true)
    @Cacheable(key = "#memberId", value = RedisPolicy.MEMBER_KEY)
    public MemberResponse findById(Long memberId) {
        log.info("Call findById");
        Member member =  memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        return MemberResponse.of(member);
    }

    @Transactional(readOnly = true)
    public void emailDuplicateCheck(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(ErrorCode.EMAIL_DUPLICATE);
        }
    }

    @Transactional(readOnly = true)
    public void nicknameDuplicateCheck(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new DuplicateNicknameException(ErrorCode.NICKNAME_DUPLICATE);
        }
    }


}
