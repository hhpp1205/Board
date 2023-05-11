package team.cupid.realworld.domain.good.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import team.cupid.realworld.domain.good.dto.CommonGoodResponseDto;
import team.cupid.realworld.domain.good.service.GoodService;
import team.cupid.realworld.global.security.principal.CustomUserDetails;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/good")
public class GoodController {

    private final GoodService goodService;

    @PostMapping("like/{boardId}")
    public ResponseEntity<CommonGoodResponseDto> like(
            @PathVariable("boardId") Long boardId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        CommonGoodResponseDto responseDto = goodService.like(boardId, customUserDetails.getId());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PostMapping("cancel/{boardId}")
    public ResponseEntity<CommonGoodResponseDto> cancel(
            @PathVariable("boardId") Long boardId,
            @AuthenticationPrincipal CustomUserDetails customUserDetails
    ) {
        CommonGoodResponseDto responseDto = goodService.cancel(boardId, customUserDetails.getId());

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }
}
