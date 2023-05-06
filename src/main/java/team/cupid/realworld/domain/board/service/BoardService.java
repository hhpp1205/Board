package team.cupid.realworld.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cupid.realworld.domain.board.domain.Board;
import team.cupid.realworld.domain.board.domain.BoardStatus;
import team.cupid.realworld.domain.board.domain.repository.BoardRepository;
import team.cupid.realworld.domain.board.domain.tag.*;
import team.cupid.realworld.domain.board.dto.*;
import team.cupid.realworld.domain.member.domain.Member;
import team.cupid.realworld.domain.member.domain.repository.MemberRepository;
import team.cupid.realworld.domain.member.exception.MemberNotFoundException;
import team.cupid.realworld.global.security.principal.CustomUserDetails;

import javax.persistence.EntityNotFoundException;
import javax.validation.constraints.NotBlank;
import java.util.*;
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

        for (String tagName : request.getTags()) {
            Tag tag;
            if (tagRepository.existsByName(tagName)) {
                tag = tagRepository.findByName(tagName).orElseThrow(() -> new EntityNotFoundException());
            } else {
                tag = Tag.of(tagName);
            }
            tagRepository.save(tag);
            boardTagRepository.save(BoardTag.of(board, tag));
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

        /**
         * 게시글 작성자와 현재 로그인한 사용자가 같지 않을 때 예외처리
         */


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

    public ResponseEntity<String> updateBoard2(BoardUpdateDto request, Long memberId) {
        Board board = boardRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        //기존 태그를 가져온다
        List<Long> tagIds = board.getBoardTags().stream()
                .map(boardTag -> boardTag.getTag().getId())
                .collect(Collectors.toList());

        List<Tag> tags = tagRepository.findAllByIdIn(tagIds);

        Map<String, Boolean> tagUseCheckMap = new HashMap<>();
        for (Tag tag : tags) {
            tagUseCheckMap.put(tag.getName(), false);
        }

        //기존 태그와 변경 태그를 비교하며 사용하는 태그에 체크(true)한다.
        //새로운 태그는 추가한다.
        for (String tagName : request.getTags()) {
            // 계속 사용되는 태그
            if(tagUseCheckMap.get(tagName) != null) {
                tagUseCheckMap.put(tagName, true);
            // 새로 추가될 태그
            } else {
                if (!tagRepository.existsByName(tagName)) {
                    tagRepository.save(Tag.of(tagName));
                }

                Tag tag = tagRepository.findByName(tagName).orElseThrow(() -> new EntityNotFoundException());

                boardTagRepository.save(BoardTag.of(board, tag));
            }
        }

        //기존에 있던 태그를 지울 수도 있다.
        for (String tagName : tagUseCheckMap.keySet()) {
            if(tagUseCheckMap.get(tagName) == false) {
                Tag tag = tagRepository.findByName(tagName).orElseThrow(() -> new EntityNotFoundException());
                boardTagRepository.deleteById(board.getId(), tag.getId());
            }
        }

        board.update(request.toEntity());

        return ResponseEntity.status(HttpStatus.OK).body("게시글 업데이트 성공");
    }

    public ResponseEntity<String> deleteBoard(BoardDeleteDto request, Long memberId) {
        Board board = boardRepository.findById(request.getId())
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        /**
         * 게시글 작성자와 현재 로그인한 사용자가 같지 않을 때 예외처리
         */

        boardRepository.delete(board);

        return ResponseEntity.status(HttpStatus.OK).body("게시글 삭제 성공");
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
