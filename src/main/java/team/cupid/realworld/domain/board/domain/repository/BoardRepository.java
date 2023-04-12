package team.cupid.realworld.domain.board.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team.cupid.realworld.domain.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long>, CustomBoardRepository {

    Boolean existsCreateTimeById(Long id);
}
