package team.cupid.realworld.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import team.cupid.realworld.domain.board.domain.Board;
import team.cupid.realworld.domain.board.domain.BoardStatus;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BoardSaveResponseDto {
    private Long boardId;
    private String title;
    private String content;
    private List<String> tags;
    private BoardStatus status;

    public static BoardSaveResponseDto of(Board board, List<String> tags) {
        return BoardSaveResponseDto.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .tags(tags)
                .status(board.getBoardStatus())
                .build();
    }
}
