//package team.cupid.realworld.global.controller;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import team.cupid.realworld.global.service.BoardService;
//
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/board")
//public class BoardController {
//
//
//    private String final Logger logger = LoggerFactory.getLogger(BoardController.class);
//
//    @Autowired
//    private final BoardService boardService;
//
//    public BoardController(BoardService boardService) {
//        this.boardService = boardService;
//    }
//
//    @GetMapping("/list")
//    public String boardList(Model model) {
//        model.addAttribute("boardList", boardService.findBoardList);
//
//        return "/board/list";
//    }
//
//
//
//
//}
