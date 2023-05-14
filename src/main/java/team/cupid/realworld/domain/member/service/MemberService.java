package team.cupid.realworld.domain.member.service;

import lombok.RequiredArgsConstructor;
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

    public Long update(Long memberId, Member member) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        if (findMember.getNickname() != member.getNickname()) {
            nicknameDuplicateCheck(member.getNickname());
        }

        findMember.update(member);
        return findMember.getMemberId();
    }

    public void delete(Long memberId) {
        memberRepository.deleteById(memberId);
    }

    public MemberResponse findByNickname(String nickname) {
        return MemberResponse.of(memberRepository.findByNickname(nickname)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND)));
    }

    public void emailDuplicateCheck(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException(ErrorCode.EMAIL_DUPLICATE);
        }
    }

    public void nicknameDuplicateCheck(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new DuplicateNicknameException(ErrorCode.NICKNAME_DUPLICATE);
        }
    }
}
