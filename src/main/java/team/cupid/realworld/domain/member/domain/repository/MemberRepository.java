package team.cupid.realworld.domain.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.cupid.realworld.domain.member.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long>, MemberCustomRepository {
}
