package team.cupid.realworld.domain.good.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cupid.realworld.domain.board.domain.Board;
import team.cupid.realworld.domain.board.domain.repository.BoardRepository;
import team.cupid.realworld.domain.good.domain.Good;
import team.cupid.realworld.domain.good.domain.repository.GoodRepository;
import team.cupid.realworld.domain.member.domain.Member;
import team.cupid.realworld.domain.member.domain.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodService{

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final GoodRepository goodRepository;

    public ResponseEntity<String> like(Long boardId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member가 존재하지 않습니다."));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board가 존재하지 않습니다."));

        Good good = null;
        if (goodRepository.existsByBoardIdAndMemberMemberId(board.getId(), member.getMemberId())) {

            good = goodRepository.findByBoardIdAndMemberMemberId(board.getId(), member.getMemberId())
                    .orElseThrow(() -> new RuntimeException("good이 존재하지 않습니다."));
            good.updateGood(true);
        } else {

            good = goodRepository.save(good.of(board, member));
        }

        board.increaseGoodCount();

        return ResponseEntity.status(HttpStatus.OK).body("좋아요 상태 업데이트 성공");
    }

    public ResponseEntity<String> cancel(Long boardId, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member가 존재하지 않습니다."));

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("Board가 존재하지 않습니다."));

        Good good = goodRepository.findByBoardIdAndMemberMemberId(board.getId(), member.getMemberId())
                .orElseThrow(() -> new RuntimeException("good이 존재하지 않습니다."));

        good.updateGood(false);
        board.decreaseGoodCount();

        return ResponseEntity.status(HttpStatus.OK).body("좋아요 상태 업데이트 성공");
    }
}
