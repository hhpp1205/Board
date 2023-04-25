package team.cupid.realworld.domain.board.domain.tag;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {
}
