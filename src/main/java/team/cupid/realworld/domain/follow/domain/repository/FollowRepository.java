package team.cupid.realworld.domain.follow.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.cupid.realworld.domain.follow.domain.Follow;
import team.cupid.realworld.domain.member.domain.Member;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    public boolean existsByFromMemberAndToMember(Member fromMember, Member toMember);
}
