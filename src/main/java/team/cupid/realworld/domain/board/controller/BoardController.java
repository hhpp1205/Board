package team.cupid.realworld.domain.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.cupid.realworld.domain.board.dto.*;
import team.cupid.realworld.domain.board.service.BoardService;
import team.cupid.realworld.global.security.principal.CustomUserDetails;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardSaveResponseDto> saveBoard(
            @RequestBody @Valid final BoardSaveRequestDto request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        BoardSaveResponseDto responseDto = boardService.save(request, customUserDetails.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
    }

    @GetMapping("/search/all")
    public ResponseEntity<List<BoardReadResponseDto>> searchBoardAll(
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        List<BoardReadResponseDto> responseDto = boardService.searchAll(customUserDetails.getId());

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/search")
    public ResponseEntity<PageInfoResponseDto> searchBoardPage(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) Integer pageNumber,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        PageInfoResponseDto responseDto = boardService.searchPage(customUserDetails.getId(), pageNumber);

        return ResponseEntity.ok(responseDto);
    }

    @PatchMapping
    public ResponseEntity<BoardUpdateResponseDto> updateBoard(
            @RequestBody @Valid final BoardUpdateRequestDto request,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        BoardUpdateResponseDto responseDto = boardService.update(request, customUserDetails.getId());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<Void> deleteReport(
            @PathVariable Long boardId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        return boardService.delete(boardId, customUserDetails.getId());
    }

}
