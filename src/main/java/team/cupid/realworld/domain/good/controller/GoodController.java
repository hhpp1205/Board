package team.cupid.realworld.domain.good.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team.cupid.realworld.domain.good.dto.GoodStateUpdateDto;
import team.cupid.realworld.domain.good.service.GoodService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/good")
public class GoodController {

    private final GoodService goodService;

    @PatchMapping
    public ResponseEntity<String> updateState(
            @RequestBody @Valid final GoodStateUpdateDto request
    ) {
        return goodService.updateState(request);
    }
}
