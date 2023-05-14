package team.cupid.realworld.domain.board.exception;

import team.cupid.realworld.global.error.exception.BusinessException;
import team.cupid.realworld.global.error.exception.ErrorCode;

public class BoardTagNotFoundException extends BusinessException {
    public BoardTagNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public BoardTagNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
