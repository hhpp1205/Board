package team.cupid.realworld.domain.board.dto;

import lombok.Getter;
import team.cupid.realworld.domain.board.domain.Board;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class BoardUpdateDto {
    @NotNull
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
    private List<@NotBlank String> tags;

    public Board toEntity() {
        return Board.builder()
                .title(title)
                .content(content)
                .build();
    }
}
