package team.cupid.realworld.domain.good.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cupid.realworld.domain.good.dto.GoodStateUpdateDto;

@Service
@Transactional
public class GoodService{


    public ResponseEntity<String> updateState(GoodStateUpdateDto request) {

        return null;
    }
}
