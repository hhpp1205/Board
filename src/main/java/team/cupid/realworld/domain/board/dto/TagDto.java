package team.cupid.realworld.domain.board.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class TagDto {
    private String name;

    public TagDto(String name) {
        this.name = name;
    }
}
