package com.adnstyle.dalnu_gw.controller;

import com.adnstyle.dalnu_gw.domain.Board;
import com.adnstyle.dalnu_gw.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/apis/board")
public class BoardController {
    
    private final BoardService boardService;
    
    /**
     * 게시판 글 등록
     */
    @PostMapping
    public void insertBoard(@RequestBody Board board){
        boardService.insertBoard(board);
    }
    
    
}
