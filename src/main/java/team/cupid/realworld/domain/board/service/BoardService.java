package team.cupid.realworld.domain.board.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.cupid.realworld.domain.board.dto.BoardDeleteDto;
import team.cupid.realworld.domain.board.dto.BoardReadDto;
import team.cupid.realworld.domain.board.dto.BoardSaveDto;
import team.cupid.realworld.domain.board.dto.BoardUpdateDto;

import java.util.List;

@Service
@Transactional
public class BoardService {
    public ResponseEntity<String> saveBoard(BoardSaveDto request) {

        return null;
    }

    public ResponseEntity<List<BoardReadDto>> readBoardList() {

        return null;
    }

    public ResponseEntity<String> updateBoard(BoardUpdateDto request) {

        return null;
    }

    public ResponseEntity<String> deleteBoard(BoardDeleteDto request) {

        return null;
    }
}
