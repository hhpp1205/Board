package team.cupid.realworld.domain.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.cupid.realworld.domain.member.domain.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {

    Optional<Member> findByEmail(String email);
    Optional<Member> findByNickname(String nickname);
    boolean existsByEmail(String email);
    boolean existsByNickname(String email);


}
