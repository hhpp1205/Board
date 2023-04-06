package team.cupid.realworld.domain.member.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cupid.realworld.domain.member.domain.Member;
import team.cupid.realworld.domain.member.domain.repository.MemberRepository;
import team.cupid.realworld.domain.member.dto.MemberResponse;
import team.cupid.realworld.domain.member.exception.DuplicateEmailException;
import team.cupid.realworld.domain.member.exception.DuplicateNicknameException;
import team.cupid.realworld.domain.member.exception.MemberNotFoundException;


@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public Long create(Member member) {
        emailDuplicateCheck(member.getNickname());
        nicknameDuplicateCheck(member.getNickname());

        return memberRepository.save(member).getMemberId();
    }

    public Long update(Long memberId, Member member) {
        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member를 찾을 수 없습니다."));

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
                .orElseThrow(() -> new MemberNotFoundException("Member를 찾을 수 없습니다.")));
    }

    public void emailDuplicateCheck(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }

    public void nicknameDuplicateCheck(String nickname) {
        if (memberRepository.existsByNickname(nickname)) {
            throw new DuplicateNicknameException();
        }
    }
}
