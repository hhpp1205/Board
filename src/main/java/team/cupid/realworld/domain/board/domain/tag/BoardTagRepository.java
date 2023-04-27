package team.cupid.realworld.domain.board.domain.tag;

import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.cupid.realworld.domain.board.domain.Board;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardTagRepository extends JpaRepository<BoardTag, Long> {

    Optional<List<BoardTag>> findAllByBoardId(Long boardId);

    boolean existsByBoardAndTag(Board board, Tag tag);
}
