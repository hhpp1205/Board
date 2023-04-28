package team.cupid.realworld.domain.board.domain.tag;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BoardTagId implements Serializable {

    private Long board;
    private Long tag;
}
