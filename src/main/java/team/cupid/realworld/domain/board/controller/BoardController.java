package team.cupid.realworld.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import team.cupid.realworld.domain.board.dto.BoardDeleteDto;
import team.cupid.realworld.domain.board.dto.BoardReadDto;
import team.cupid.realworld.domain.board.dto.BoardSaveDto;
import team.cupid.realworld.domain.board.dto.BoardUpdateDto;
import team.cupid.realworld.domain.board.service.BoardService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping()
    public ResponseEntity saveBoard(
            @RequestBody @Valid final BoardSaveDto request
    ) {
        return boardService.saveBoard(request);
    }

    @GetMapping("/list")
    public ResponseEntity<List<BoardReadDto>> readBoardList(
    ) {
        return boardService.readBoardList();
    }

    @PatchMapping()
    public ResponseEntity<String> updateBoard(
            @RequestBody @Valid final BoardUpdateDto request
    ) {
        return boardService.updateBoard(request);
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteReport(
            @RequestBody @Valid final BoardDeleteDto request
    ) {
        return boardService.deleteBoard(request);
    }

}
