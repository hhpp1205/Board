package team.cupid.realworld.domain.report.domain;

import lombok.Getter;

@Getter
public enum ReportType {

    SEXUAL_POSTS("성적인 내용이 포함된 게시물"),
    SEXUAL_COMMENTS("성적인 내용이 포함된 댓글");

    private String reason;

    private ReportType(String reason) {
        this.reason = reason;
    }
}
