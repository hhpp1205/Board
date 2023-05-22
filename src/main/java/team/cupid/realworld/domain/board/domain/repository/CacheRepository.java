package team.cupid.realworld.domain.board.domain.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import team.cupid.realworld.domain.board.dto.BoardReadResponseDto;
import team.cupid.realworld.domain.board.exception.BoardNotFoundException;
import team.cupid.realworld.global.error.exception.ErrorCode;

import static team.cupid.realworld.global.policy.RedisPolicy.BOARD_KEY;

@Component
@RequiredArgsConstructor
public class CacheRepository {

    private final BoardRepository boardRepository;

    @Cacheable(key = "#pageable.getPageNumber()", value = BOARD_KEY)
    public Page<BoardReadResponseDto> getBoardReadResponseDtosBy(Pageable pageable) {
        return boardRepository.searchPageBoardReadDto(pageable)
                .orElseThrow(() -> new BoardNotFoundException(ErrorCode.BOARD_NOT_FOUND));
    }

}
