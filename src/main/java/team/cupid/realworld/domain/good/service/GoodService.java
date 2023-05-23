package team.cupid.realworld.domain.good.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cupid.realworld.domain.board.domain.Board;
import team.cupid.realworld.domain.board.domain.repository.BoardRepository;
import team.cupid.realworld.domain.board.exception.BoardNotFoundException;
import team.cupid.realworld.domain.good.domain.Good;
import team.cupid.realworld.domain.good.domain.repository.GoodRepository;
import team.cupid.realworld.domain.good.dto.CommonGoodResponseDto;
import team.cupid.realworld.domain.good.exception.GoodNotFoundException;
import team.cupid.realworld.domain.good.exception.IsGoodException;
import team.cupid.realworld.domain.member.domain.Member;
import team.cupid.realworld.domain.member.domain.repository.MemberRepository;
import team.cupid.realworld.domain.member.exception.MemberNotFoundException;
import team.cupid.realworld.global.error.exception.ErrorCode;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodService{

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final GoodRepository goodRepository;

    public CommonGoodResponseDto like(Long boardId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(ErrorCode.BOARD_NOT_FOUND));

        Good good = null;
        if (goodRepository.existsByBoardIdAndMemberMemberId(board.getId(), member.getMemberId())) {

            good = goodRepository.findByBoardIdAndMemberMemberId(board.getId(), member.getMemberId())
                    .orElseThrow(() -> new GoodNotFoundException(ErrorCode.GOOD_NOT_FOUND));

            isGood(good);

            good.setIsGoodTrue();
        } else {

            good = goodRepository.save(good.of(board, member));
        }

        board.increaseGoodCount();

        return CommonGoodResponseDto.of(board, good);
    }

    public CommonGoodResponseDto cancel(Long boardId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardNotFoundException(ErrorCode.BOARD_NOT_FOUND));

        Good good = goodRepository.findByBoardIdAndMemberMemberId(board.getId(), member.getMemberId())
                .orElseThrow(() -> new GoodNotFoundException(ErrorCode.GOOD_NOT_FOUND));

        isNotGood(good);

        good.setIsGoodFalse();
        board.decreaseGoodCount();

        return CommonGoodResponseDto.of(board, good);
    }

    // exception
    public void isGood(Good good) {

        if (good.isGood()) {
            throw new IsGoodException(ErrorCode.ALREADY_LIKED);
        }
    }

    public void isNotGood(Good good) {

        if (!good.isGood()) {
            throw new IsGoodException(ErrorCode.NOT_LIKED);
        }
    }
}
