package team.cupid.realworld.domain.board.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import team.cupid.realworld.domain.board.domain.Board;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class BoardUpdateResponseDto {

    private Long boardId;

    private String title;

    private String content;

    private List<String> tags;

    public static BoardUpdateResponseDto of(Board board, List<String> tags) {
        return BoardUpdateResponseDto.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .tags(tags)
                .build();
    }
}
