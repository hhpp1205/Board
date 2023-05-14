package team.cupid.realworld.domain.board.exception;

import team.cupid.realworld.global.error.exception.BusinessException;
import team.cupid.realworld.global.error.exception.ErrorCode;

public class CustomEntityNotFoundException extends BusinessException {
    public CustomEntityNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public CustomEntityNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
