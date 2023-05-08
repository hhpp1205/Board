package team.cupid.realworld.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BoardSaveResponseDto {
    private Long boardId;

    public static BoardSaveResponseDto of(Long boardId) {
        return BoardSaveResponseDto.builder()
                .boardId(boardId)
                .build();
    }
}
