package team.cupid.realworld.domain.board.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import team.cupid.realworld.domain.board.domain.Board;
import team.cupid.realworld.domain.board.domain.repository.BoardRepository;
import team.cupid.realworld.domain.board.domain.tag.BoardTag;
import team.cupid.realworld.domain.board.domain.tag.BoardTagRepository;
import team.cupid.realworld.domain.board.domain.tag.Tag;
import team.cupid.realworld.domain.board.domain.tag.TagRepository;
import team.cupid.realworld.domain.board.dto.BoardUpdateRequestDto;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private TagRepository tagRepository;

    @Autowired
    private BoardTagRepository boardTagRepository;

    @DisplayName("게시물 정보 변경 시 태그변경")
    @Test
    void test() {
        //given
        Tag tag = Tag.of("여행");
        tagRepository.save(tag);

        Board board = Board.builder()
                .id(1L)
                .title("세계일주")
                .content("Hi")
                .build();
        boardRepository.save(board);

        BoardTag boardTag = BoardTag.of(board, tag);
        boardTagRepository.save(boardTag);

        BoardUpdateRequestDto request = BoardUpdateRequestDto.builder()
                .id(1L)
                .title("전국여행")
                .content("Hi Hi")
                .tags(List.of("여행", "전국일주"))
                .build();

        //when
//        boardService.updateBoard2(request, 1L);

        //then
        Board findBoard = boardRepository.findById(1L).get();
        assertThat(findBoard)
                .extracting("title", "content")
                .contains("전국여행", "Hi Hi");

    }

}