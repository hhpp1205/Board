package team.cupid.realworld.domain.board.exception;

import team.cupid.realworld.global.error.exception.BusinessException;
import team.cupid.realworld.global.error.exception.ErrorCode;

public class BoardNotFoundException extends BusinessException {
    public BoardNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public BoardNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
