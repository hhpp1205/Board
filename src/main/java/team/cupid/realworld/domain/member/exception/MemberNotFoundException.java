package team.cupid.realworld.domain.member.exception;

import team.cupid.realworld.global.error.exception.BusinessException;
import team.cupid.realworld.global.error.exception.ErrorCode;

public class MemberNotFoundException extends BusinessException {
    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
