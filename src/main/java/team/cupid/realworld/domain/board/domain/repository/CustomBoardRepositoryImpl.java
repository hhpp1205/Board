package team.cupid.realworld.domain.board.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import team.cupid.realworld.domain.board.domain.BoardStatus;
import team.cupid.realworld.domain.board.domain.QBoard;
import team.cupid.realworld.domain.board.domain.tag.QBoardTag;
import team.cupid.realworld.domain.board.domain.tag.QTag;
import team.cupid.realworld.domain.board.dto.BoardReadDto;
import team.cupid.realworld.domain.board.dto.TagDto;
import team.cupid.realworld.domain.good.domain.QGood;
import team.cupid.realworld.domain.member.domain.QMember;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.querydsl.core.group.GroupBy.groupBy;

import static com.querydsl.core.types.Projections.list;
import static java.util.stream.Collectors.toList;
import static team.cupid.realworld.domain.board.domain.QBoard.board;
import static team.cupid.realworld.domain.board.domain.tag.QBoardTag.boardTag;
import static team.cupid.realworld.domain.board.domain.tag.QTag.tag;
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
        QBoardTag bt = boardTag;
        QTag t = tag;

        List<BoardReadDto> list =
                queryFactory.selectFrom(b)
                        .join(m).on(b.member.memberId.eq(m.memberId))
                        .leftJoin(g).on(b.id.eq(g.board.id).and(g.member.memberId.eq(id)))
                        .join(bt).on(b.id.eq(bt.board.id))
                        .join(t).on(bt.tag.id.eq(t.id))
                        .where(b.boardStatus.eq(BoardStatus.SAVED))
                        .orderBy(b.id.desc())
                        .transform(groupBy(b.id).list(
                                Projections.constructor(BoardReadDto.class
                                        , b.id.as("boardId")
                                        , b.title
                                        , b.content
                                        , list(Projections.constructor(TagDto.class, t.name))
                                        , m.nickname.as("writer")
                                        , b.createTime.as("createdDate")
                                        , g.isGood
                                        , b.goodCount)));

        return Optional.ofNullable(list);
    }
}

