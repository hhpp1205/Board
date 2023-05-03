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
import team.cupid.realworld.domain.good.dto.GoodStateDto;
import team.cupid.realworld.domain.member.domain.Member;
import team.cupid.realworld.domain.member.domain.repository.MemberRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class GoodService{

    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;
    private final GoodRepository goodRepository;

    public synchronized ResponseEntity<String> updateState(GoodStateDto request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member가 존재하지 않습니다."));

        Board board = boardRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("Board가 존재하지 않습니다."));

        /**
         * 1. 좋아요 테이블의 게시글 id와 유저 id를 검증하여 같은 데이터가 존재하면 수정, 존재 하지 않으면 삽입
         * 2. 좋아요 클릭 시 board 테이블의 good_count +1
         */

        Good good;
        if (goodRepository.existsByBoardIdAndMemberMemberId(board.getId(), member.getMemberId())) {
            good = goodRepository.findByBoardIdAndMemberMemberId(board.getId(), member.getMemberId())
                    .orElseThrow(() -> new RuntimeException("good이 존재하지 않습니다."));

            if (good.isGood()) {
                good.updateGood(false);
            } else {
                good.updateGood(true);
            }

        } else {
            good = goodRepository.save(request.toEntity(board, member));
        }

        if (good.isGood()) {
            board.increaseGoodCount();
        } else {
            board.decreaseGoodCount();
        }

        return ResponseEntity.status(HttpStatus.OK).body("좋아요 상태 업데이트 성공");
    }
}
