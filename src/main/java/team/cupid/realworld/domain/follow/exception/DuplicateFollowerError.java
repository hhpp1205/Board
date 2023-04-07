package team.cupid.realworld.domain.follow.exception;

import team.cupid.realworld.global.error.exception.BusinessException;
import team.cupid.realworld.global.error.exception.ErrorCode;

public class DuplicateFollowerError extends BusinessException {
    public DuplicateFollowerError(ErrorCode errorCode) {
        super(errorCode);
    }
}
