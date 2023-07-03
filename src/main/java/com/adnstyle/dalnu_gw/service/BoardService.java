package com.adnstyle.dalnu_gw.service;

import com.adnstyle.dalnu_gw.domain.Board;
import com.adnstyle.dalnu_gw.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

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
    
    /**
     * 게시판 상세조회
     * @param uid
     * @return
     */
    public Board selectBoard(String uid) {
      return boardRepository.selectBoard(uid);
    }
    
    /**
     * 게시판 전체 조회
     * @return
     */
    public List<Board> selectBoardList() {
        return boardRepository.selectBoardList();
    }
    
    /**
     * 게시판 수정
     * @param board
     */
    public void updateBoard(Board board) {
        boardRepository.updateBoard(board);
    }
    
    /**
     * 게시판 삭제
     * @param uid
     */
    public void deleteBoard(String uid) {
        boardRepository.deleteBoard(uid);
    }
    

}
