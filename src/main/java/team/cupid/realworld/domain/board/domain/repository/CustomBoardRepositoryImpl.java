package team.cupid.realworld.domain.board.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.cupid.realworld.domain.board.domain.QBoard;
import team.cupid.realworld.domain.board.dto.BoardReadDto;
import team.cupid.realworld.domain.good.domain.QGood;
import team.cupid.realworld.domain.member.domain.QMember;

import java.util.List;
import java.util.Optional;

import static com.querydsl.core.types.ExpressionUtils.template;
import static com.querydsl.jpa.JPAExpressions.select;
import static team.cupid.realworld.domain.board.domain.QBoard.board;
import static team.cupid.realworld.domain.good.domain.QGood.good;
import static team.cupid.realworld.domain.member.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class CustomBoardRepositoryImpl implements CustomBoardRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<BoardReadDto>> findAllBoardReadDto(Long id) {
        QBoard b = board;
        QMember m = member;
        QGood g = good;

        List<BoardReadDto> list =
                queryFactory
                        .select(Projections.constructor(BoardReadDto.class
                                , b.id.as("boardId")
                                , b.title
                                , b.content
                                , m.nickname.as("writer")
                                , b.createTime.as("createdDate")
                                , new CaseBuilder()
                                        .when(g.member.memberId.eq(id).and(g.isGood.eq(true)))
                                        .then(true)
                                        .otherwise(false).as("isGood")
                                , new CaseBuilder()
                                        .when(g.isGood.eq(true))
                                        .then(1)
                                        .otherwise(0)
                                        .count().as("goodCount")))
                        .from(b)
                        .join(m).on(b.member.memberId.eq(m.memberId))
                        .leftJoin(g).on(b.id.eq(g.board.id))
                        .groupBy(b.id, b.title, b.content, b.createTime, m.nickname, g.isGood, g.member.memberId)
                        .orderBy(b.id.desc())
                        .fetch();

        return Optional.ofNullable(list);
    }
}

