package team.cupid.realworld.domain.report.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import team.cupid.realworld.global.common.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Report extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id", updatable = false)
    private Long id;

    @Column(name = "title", length = 30, unique = true)
    private String title;

    @Column(name = "content", length = 300)
    private String content;

    @Column(name = "target_user_id")
    private Long targetUserId;

    //회원 테이블 연관관계 추가 예정 (신고자)

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ReportType type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (id == null || !(o instanceof Report)) return false;

        final Report report = (Report) o;

        return id.equals(report.getId());
    }

    @Override
    public int hashCode() {
        return id.intValue();
    }
}
