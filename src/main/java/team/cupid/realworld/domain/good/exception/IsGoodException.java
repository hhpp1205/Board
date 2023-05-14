package team.cupid.realworld.domain.good.exception;

import team.cupid.realworld.global.error.exception.BusinessException;
import team.cupid.realworld.global.error.exception.ErrorCode;

public class IsGoodException extends BusinessException {
    public IsGoodException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public IsGoodException(ErrorCode errorCode) {
        super(errorCode);
    }
}
