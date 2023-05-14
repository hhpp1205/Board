package team.cupid.realworld.domain.member.exception;

import team.cupid.realworld.global.error.exception.BusinessException;
import team.cupid.realworld.global.error.exception.ErrorCode;

public class DuplicateNicknameException extends BusinessException {

    public DuplicateNicknameException(ErrorCode errorCode) {
        super(errorCode);
    }
}
