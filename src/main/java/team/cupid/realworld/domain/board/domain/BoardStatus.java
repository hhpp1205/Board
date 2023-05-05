package team.cupid.realworld.domain.board.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum BoardStatus {
    DELETE, TEMPORARY, SAVED;

    @JsonCreator
    public static BoardStatus fromString(String value) {
        return BoardStatus.valueOf(value.toUpperCase());
    }
}
