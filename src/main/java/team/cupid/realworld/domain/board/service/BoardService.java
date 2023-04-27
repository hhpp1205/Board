package team.cupid.realworld.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cupid.realworld.domain.board.domain.Board;
import team.cupid.realworld.domain.board.domain.BoardStatus;
import team.cupid.realworld.domain.board.domain.repository.BoardRepository;
import team.cupid.realworld.domain.board.domain.tag.BoardTag;
import team.cupid.realworld.domain.board.domain.tag.BoardTagRepository;
import team.cupid.realworld.domain.board.domain.tag.Tag;
import team.cupid.realworld.domain.board.domain.tag.TagRepository;
import team.cupid.realworld.domain.board.dto.*;
import team.cupid.realworld.domain.member.domain.Member;
import team.cupid.realworld.domain.member.domain.repository.MemberRepository;
import team.cupid.realworld.domain.member.exception.MemberNotFoundException;
import team.cupid.realworld.global.security.principal.CustomUserDetails;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final BoardTagRepository boardTagRepository;

    public ResponseEntity<String> saveBoard(BoardSaveDto request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException("Member가 존재하지 않습니다."));

        Board board = boardRepository.save(request.toEntity(member));

        Iterator<String> nameIterator = request.getTags().listIterator();

        while(nameIterator.hasNext()) {
            boardTagRepository.save(
                    BoardTag.builder()
                            .board(board)
                            .tag(getTag(nameIterator.next()))
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

    public ResponseEntity<String> updateBoard(BoardUpdateDto request, Long memberId) {
        Board board = boardRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        List<Tag> tagList = new ArrayList<>();
        Iterator<String> nameList = request.getTags().listIterator();
        while(nameList.hasNext()) {
            tagList.add(getTag(nameList.next()));
        }

        List<BoardTag> boardTagList = boardTagRepository.findAllByBoardId(request.getId())
                .orElseThrow(() -> new RuntimeException("태그가 저장되어있지 않습니다."));

        Iterator<Tag> tagIterator;
        Iterator<BoardTag> boardTagIterator;

        // 기존 태그에 수정 태그가 없을 때
        tagIterator = tagList.listIterator();
        while(tagIterator.hasNext()) {
            Tag tag = tagIterator.next();

            int cnt = 0;
            boardTagIterator = boardTagList.listIterator();
            while (boardTagIterator.hasNext()) {
                BoardTag bt = boardTagIterator.next();

                if (bt.getTag().equals(tag)) { // 동등성 비교 고려
                    cnt++;
                    break;
                }

            }
            if (cnt == 0) {
                boardTagRepository.save(
                        BoardTag.builder()
                                .board(board)
                                .tag(tag)
                                .build());
            }
        }

        // 수정 태그에 기존 태그가 없을 때
        boardTagIterator = boardTagList.listIterator();
        while(boardTagIterator.hasNext()) {
            BoardTag bt = boardTagIterator.next();

            int cnt = 0;
            tagIterator = tagList.listIterator();
            while (tagIterator.hasNext()) {
                Tag tag = tagIterator.next();

                if (tag.equals(bt.getTag())) { // 동등성 비교 고려
                    cnt++;
                    break;
                }
            }
            if (cnt == 0) {
                boardTagRepository.delete(bt);
            }
        }

        board.update(request.toEntity());

        return ResponseEntity.status(HttpStatus.OK).body("게시글 업데이트 성공");
    }

    public ResponseEntity<String> deleteBoard(BoardDeleteDto request) {

        return null;
    }

    // 메서드

    public Tag getTag(String s) {
        Tag tag;

        if (tagRepository.existsByName(s)) {
            tag = tagRepository.findByName(s)
                    .orElseThrow(() -> new RuntimeException("tag가 존재하지 않습니다"));
        } else {
            tag = tagRepository.save(
                    Tag.builder()
                            .name(s)
                            .build());
        }

        return tag;
    }

    // 예외 처리
}
