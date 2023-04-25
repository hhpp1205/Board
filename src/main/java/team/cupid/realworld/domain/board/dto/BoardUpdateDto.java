package team.cupid.realworld.domain.board.dto;

import lombok.Getter;
import team.cupid.realworld.domain.board.domain.Board;

import javax.validation.constraints.NotBlank;

@Getter
public class BoardUpdateDto {
    @NotBlank
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    public Board toEntity() {
        return Board.builder()
                .title(this.title)
                .content(this.content)
                .build();
    }
}
