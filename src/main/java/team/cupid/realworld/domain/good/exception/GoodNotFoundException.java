package team.cupid.realworld.domain.good.exception;

import team.cupid.realworld.global.error.exception.BusinessException;
import team.cupid.realworld.global.error.exception.ErrorCode;

public class GoodNotFoundException extends BusinessException {
    public GoodNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public GoodNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
