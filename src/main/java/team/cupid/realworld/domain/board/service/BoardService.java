package team.cupid.realworld.domain.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cupid.realworld.domain.board.domain.Board;
import team.cupid.realworld.domain.board.domain.repository.BoardRepository;
import team.cupid.realworld.domain.board.domain.tag.*;
import team.cupid.realworld.domain.board.dto.*;
import team.cupid.realworld.domain.board.exception.BoardNotFoundException;
import team.cupid.realworld.domain.board.exception.BoardTagNotFoundException;
import team.cupid.realworld.domain.board.exception.NoMatchBoardWriterException;
import team.cupid.realworld.domain.board.exception.TagNotFoundException;
import team.cupid.realworld.domain.member.domain.Member;
import team.cupid.realworld.domain.member.domain.repository.MemberRepository;
import team.cupid.realworld.domain.member.exception.MemberNotFoundException;
import team.cupid.realworld.global.error.exception.ErrorCode;

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

    public BoardSaveResponseDto save(BoardSaveRequestDto request, Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberNotFoundException(ErrorCode.MEMBER_NOT_FOUND));

        Board board = boardRepository.save(request.toEntity(member));

        for (String tagName : request.getTags()) {
            Tag tag;
            if (tagRepository.existsByName(tagName)) {
                tag = tagRepository.findByName(tagName).orElseThrow(() -> new TagNotFoundException(ErrorCode.TAG_NOT_FOUND));
            } else {
                tag = Tag.of(tagName);
                tagRepository.save(tag);
            }
            boardTagRepository.save(BoardTag.of(board, tag));
        }

        List<String> tagList = getTagNameList(board.getId());

        return BoardSaveResponseDto.of(board, tagList);
    }

    @Transactional(readOnly = true)
    public List<BoardReadResponseDto> searchAll(Long memberId) {
        List<BoardReadResponseDto> list = boardRepository.searchAllBoardReadDto(memberId)
                .orElseThrow(() -> new BoardNotFoundException(ErrorCode.BOARD_NOT_FOUND));

        for (BoardReadResponseDto responseDto : list) {
            responseDto.setTags(getTagNameList(responseDto.getBoardId()));
        }

        return list;
    }

    @Transactional(readOnly = true)
    public PageInfoResponseDto searchPage(Long memberId, Integer pageNo) {
        Integer pageSize = 10;
        Pageable pageable = PageRequest.of(pageNo, pageSize);

        Page<BoardReadResponseDto> page = boardRepository.searchPageBoardReadDto(memberId, pageable)
                .orElseThrow(() -> new BoardNotFoundException(ErrorCode.BOARD_NOT_FOUND));

        List<BoardReadResponseDto> list = page.getContent();

        for (BoardReadResponseDto responseDto : list) {
            responseDto.setTags(getTagNameList(responseDto.getBoardId()));
        }

        return PageInfoResponseDto.of(list, pageNo, pageSize);
    }

    public BoardUpdateResponseDto update(BoardUpdateRequestDto request, Long memberId) {
        Board board = boardRepository.findById(request.getId())
                .orElseThrow(() -> new BoardNotFoundException(ErrorCode.BOARD_NOT_FOUND));

        matchBoardWriter(board, memberId);

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

                Tag tag = tagRepository.findByName(tagName)
                        .orElseThrow(() -> new TagNotFoundException(ErrorCode.TAG_NOT_FOUND));

                boardTagRepository.save(BoardTag.of(board, tag));
            }
        }

        //기존에 있던 태그를 지울 수도 있다.
        for (String tagName : tagUseCheckMap.keySet()) {
            if(tagUseCheckMap.get(tagName) == false) {
                Tag tag = tagRepository.findByName(tagName)
                        .orElseThrow(() -> new TagNotFoundException(ErrorCode.TAG_NOT_FOUND));

                boardTagRepository.deleteById(board.getId(), tag.getId());
            }
        }

        board.update(request.toEntity());

        List<String> tagList = boardTagRepository.findAllByBoardId(board.getId())
                .orElseThrow(() -> new BoardTagNotFoundException(ErrorCode.BOARD_TAG_NOT_FOUND))
                .stream().map(e -> e.getTag().getName()).collect(Collectors.toList());

        return BoardUpdateResponseDto.of(board, tagList);
    }

    public ResponseEntity<Void> delete(Long boardId, Long memberId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BoardTagNotFoundException(ErrorCode.BOARD_NOT_FOUND));

        matchBoardWriter(board, memberId);

        boardRepository.delete(board);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // common method
    public List<String> getTagNameList(Long id) {
        return boardTagRepository.findAllByBoardId(id)
                .orElseThrow(() -> new BoardTagNotFoundException(ErrorCode.BOARD_TAG_NOT_FOUND))
                .stream().map(e -> e.getTag().getName()).collect(Collectors.toList());
    }

    // exception method
    public void matchBoardWriter(Board board, Long memberId) {
        if (board.getMember().getMemberId() != memberId) {
            throw new NoMatchBoardWriterException(ErrorCode.NO_MATCH_BOARD_WRITER);
        }
    }
}
