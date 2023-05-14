package team.cupid.realworld.domain.board.exception;

import team.cupid.realworld.global.error.exception.BusinessException;
import team.cupid.realworld.global.error.exception.ErrorCode;

public class TagNotFoundException extends BusinessException {
    public TagNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public TagNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
