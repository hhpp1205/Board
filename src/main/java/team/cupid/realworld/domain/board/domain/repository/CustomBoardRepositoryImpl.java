package team.cupid.realworld.domain.board.domain.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import team.cupid.realworld.domain.board.domain.BoardStatus;
import team.cupid.realworld.domain.board.domain.QBoard;
import team.cupid.realworld.domain.board.dto.BoardReadResponseDto;
import team.cupid.realworld.domain.good.domain.QGood;
import team.cupid.realworld.domain.member.domain.QMember;

import java.util.List;
import java.util.Optional;

import static team.cupid.realworld.domain.board.domain.QBoard.board;
import static team.cupid.realworld.domain.good.domain.QGood.good;
import static team.cupid.realworld.domain.member.domain.QMember.member;

@Repository
@RequiredArgsConstructor
public class CustomBoardRepositoryImpl implements CustomBoardRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Optional<List<BoardReadResponseDto>> searchAllBoardReadDto(Long id) {
        QBoard b = board;
        QMember m = member;
        QGood g = good;

        List<BoardReadResponseDto> list =
                queryFactory.select(Projections.constructor(BoardReadResponseDto.class
                        , b.id.as("boardId")
                        , b.title
                        , b.content
                        , m.nickname.as("writer")
                        , b.createTime.as("createdDate")
                        , g.isGood
                        , b.goodCount))
                        .from(b)
                        .join(m).on(b.member.memberId.eq(m.memberId))
                        .leftJoin(g).on(b.id.eq(g.board.id).and(g.member.memberId.eq(id)))
                        .where(b.boardStatus.eq(BoardStatus.SAVED))
                        .orderBy(b.id.desc())
                        .fetch();

        return Optional.ofNullable(list);
    }

    @Override
    public Optional<Page<BoardReadResponseDto>> searchPageBoardReadDto(Pageable pageable) {
        QBoard b = board;
        QMember m = member;
        QGood g = good;

        List<BoardReadResponseDto> list =
                queryFactory.select(Projections.constructor(BoardReadResponseDto.class
                                , b.id.as("boardId")
                                , b.title
                                , b.content
                                , m.nickname.as("writer")
                                , b.createTime.as("createdDate")
                                , b.goodCount))
                        .from(b)
                        .join(m).on(b.member.memberId.eq(m.memberId))
                        .where(b.boardStatus.eq(BoardStatus.SAVED))
                        .orderBy(b.id.desc())
                        .offset(pageable.getOffset())
                        .limit(pageable.getPageSize())
                        .fetch();

        return Optional.ofNullable(new PageImpl<>(list, pageable, list.size()));
    }
}

