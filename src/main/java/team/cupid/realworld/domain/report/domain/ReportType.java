package team.cupid.realworld.domain.report.domain;

import lombok.Getter;

@Getter
public enum ReportType {

    ABUSE("욕설 및 비방"),
    SEXUALEXPRESSION("성적인 표현"),
    PORNOGRAPHY("음란물 유포"),
    ILLEGALADVERTISEMENT("불법적인 광고"),
    INVASIONOFPRIVACY("개인정보 침해"),
    OTHERTHINGS("기타");

    private String reason;

    private ReportType(String reason) {
        this.reason = reason;
    }
}
