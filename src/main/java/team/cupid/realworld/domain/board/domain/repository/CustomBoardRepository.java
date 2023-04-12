package team.cupid.realworld.domain.board.domain.repository;

import team.cupid.realworld.domain.board.dto.BoardReadDto;

import java.util.List;
import java.util.Optional;

public interface CustomBoardRepository {

    Optional<List<BoardReadDto>> findAllBoardReadDto(Long id);
}
