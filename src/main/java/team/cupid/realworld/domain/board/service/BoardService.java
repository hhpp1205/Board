package team.cupid.realworld.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cupid.realworld.domain.board.domain.Board;
import team.cupid.realworld.domain.board.domain.BoardStatus;
import team.cupid.realworld.domain.board.domain.repository.BoardRepository;
import team.cupid.realworld.domain.board.domain.tag.Tag;
import team.cupid.realworld.domain.board.domain.tag.TagRepository;
import team.cupid.realworld.domain.board.dto.*;
import team.cupid.realworld.domain.member.domain.Member;
import team.cupid.realworld.domain.member.domain.repository.MemberRepository;
import team.cupid.realworld.domain.member.exception.MemberNotFoundException;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;

    public ResponseEntity<String> saveBoard(BoardSaveDto request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member를 찾을 수 없습니다."));

        Board board = boardRepository.save(request.toEntity(member));

        Iterator<String> iterator = request.getTags().listIterator();
        while(iterator.hasNext()) {
            String s = iterator.next();
            if (s == null || s == "") continue;
            tagRepository.save(Tag.builder()
                            .name(s)
                            .board(board)
                            .build());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body("게시글 저장 성공");
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<BoardReadDto>> readBoardList(Long memberId) {
        List<BoardReadDto> list = boardRepository.findAllBoardReadDto(memberId)
                .orElseThrow(() -> new MemberNotFoundException("게시글이 존재하지 않습니다."));

        return ResponseEntity.ok(list);
    }

    public ResponseEntity<String> updateBoard(BoardUpdateDto request) {

        return null;
    }

    public ResponseEntity<String> deleteBoard(BoardDeleteDto request) {

        return null;
    }

    // 예외 처리

}
