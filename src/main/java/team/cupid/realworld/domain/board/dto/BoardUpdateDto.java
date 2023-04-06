package team.cupid.realworld.domain.board.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class BoardUpdateDto {
    @NotBlank
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String content;
}
