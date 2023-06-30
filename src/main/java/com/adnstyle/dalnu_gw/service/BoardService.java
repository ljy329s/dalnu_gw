package com.adnstyle.dalnu_gw.service;

import com.adnstyle.dalnu_gw.domain.Board;
import com.adnstyle.dalnu_gw.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {
    
    private final BoardRepository boardRepository;
    
    /**
     * 게시판 글 등록
     * @param board
     */
    public void insertBoard(Board board) {
        boardRepository.insertBoard(board);
    }
}
