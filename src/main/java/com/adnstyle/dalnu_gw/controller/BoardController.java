package com.adnstyle.dalnu_gw.controller;

import com.adnstyle.dalnu_gw.domain.Board;
import com.adnstyle.dalnu_gw.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    
    /**
     * 게시판 글 상세조회
     */
    @GetMapping("/{uid}")
    public Board selectBoard(@PathVariable("uid") String uid){
        return boardService.selectBoard(uid);
    }
    /**
     * 게시판 전체 목록 조회
     */
    @GetMapping("/{boardType}/{page}")
    public List<Board>selectBoardList(@PathVariable("boardType") String boardType,@PathVariable("page") int page){
        return boardService.selectBoardList();
    }
    
    
    /**
     * 게시판 수정
     */
    @PutMapping
    public void updateBoard(@RequestBody Board board){
        boardService.updateBoard(board);
    }
    
    /**
     * 게시판 글 삭제
     */
    @DeleteMapping
    public void deleteBoard(String uid){
        boardService.deleteBoard(uid);
    }
    
    
}
