package team.cupid.realworld.domain.member.exception;

import team.cupid.realworld.global.error.exception.BusinessException;
import team.cupid.realworld.global.error.exception.ErrorCode;

public class DuplicateEmailException extends BusinessException {

    public DuplicateEmailException(ErrorCode errorCode) {
        super(errorCode);
    }
}
