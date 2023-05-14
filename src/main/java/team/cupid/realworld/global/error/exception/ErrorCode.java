package team.cupid.realworld.global.error.exception;

public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "C001", " Invalid Input Value"),
    METHOD_NOT_ALLOWED(405, "C002", " Invalid Input Value"),
    INTERNAL_SERVER_ERROR(500, "C004", "Server Error"),
    INVALID_TYPE_VALUE(400, "C005", " Invalid Type Value"),
    HANDLE_ACCESS_DENIED(403, "C006", "Access is Denied"),

    // Board
    BOARD_NOT_FOUND(500, "B001", "Server Error"),
    BOARD_TAG_NOT_FOUND(500, "B002", "Server Error"),
    TAG_NOT_FOUND(500, "B003", "Server Error"),
    NO_MATCH_BOARD_WRITER(401, "B004", "Unauthorized"),

    // Good
    GOOD_NOT_FOUND(500, "G001", "Server Error"),
    ALREADY_LIKED(500, "G002", "Server Error"),
    NOT_LIKED(500, "G003", "Server Error"),

    //Member
    MEMBER_NOT_FOUND(500, "M001", "Member Not Found Error"),
    EMAIL_DUPLICATE(400, "M002", "Duplicate Email Error"),
    NICKNAME_DUPLICATE(400, "M003", "Duplicate Nickname Error");

    private final String code;
    private final String message;
    private final int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return code;
    }

    public int getStatus() {
        return status;
    }

}
