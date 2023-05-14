package team.cupid.realworld.domain.board.exception;

import team.cupid.realworld.global.error.exception.BusinessException;
import team.cupid.realworld.global.error.exception.ErrorCode;

public class NoMatchBoardWriterException extends BusinessException {
    public NoMatchBoardWriterException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public NoMatchBoardWriterException(ErrorCode errorCode) {
        super(errorCode);
    }
}
