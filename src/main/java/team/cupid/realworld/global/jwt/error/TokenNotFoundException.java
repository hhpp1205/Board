package team.cupid.realworld.global.jwt.error;

import team.cupid.realworld.global.error.exception.BusinessException;
import team.cupid.realworld.global.error.exception.ErrorCode;

public class TokenNotFoundException extends BusinessException {
    public TokenNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
