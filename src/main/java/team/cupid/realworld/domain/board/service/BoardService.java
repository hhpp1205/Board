package team.cupid.realworld.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cupid.realworld.domain.board.domain.Board;
import team.cupid.realworld.domain.board.domain.repository.BoardRepository;
import team.cupid.realworld.domain.board.domain.tag.*;
import team.cupid.realworld.domain.board.dto.*;
import team.cupid.realworld.domain.member.domain.Member;
import team.cupid.realworld.domain.member.domain.repository.MemberRepository;
import team.cupid.realworld.domain.member.exception.MemberNotFoundException;

import javax.persistence.EntityNotFoundException;
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

    public Long saveBoard(BoardSaveRequestDto request, Long memberId) {
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

        return board.getId();
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<BoardReadResponseDto>> readBoardList(Long memberId) {
        List<BoardReadResponseDto> list = boardRepository.findAllBoardReadDto(memberId)
                .orElseThrow(() -> new MemberNotFoundException("게시글이 존재하지 않습니다."));

        return ResponseEntity.ok(list);
    }

    public BoardUpdateResponseDto updateBoard(BoardUpdateRequestDto request, Long memberId) {
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

        List<String> tagList = boardTagRepository.findAllByBoardId(board.getId())
                .orElseThrow(() -> new RuntimeException(""))
                .stream().map(e -> e.getTag().getName()).collect(Collectors.toList());

        BoardUpdateResponseDto responseDto = BoardUpdateResponseDto.of(board, tagList);

        return responseDto;
    }

    public ResponseEntity<Void> deleteBoard(Long boardId, Long memberId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new RuntimeException("게시글이 존재하지 않습니다."));

        /**
         * 게시글 작성자와 현재 로그인한 사용자가 같지 않을 때 예외처리
         */

        boardRepository.delete(board);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
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
