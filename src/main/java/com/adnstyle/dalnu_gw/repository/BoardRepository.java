package com.adnstyle.dalnu_gw.repository;

import com.adnstyle.dalnu_gw.domain.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardRepository {
    
    /**
     * 게시판 글 상세조회
     * @param uid
     * @return
     */
    Board selectBoard(String uid);
    
    /**
     * 게시판 글 등록
     * @param board
     */
    void insertBoard(Board board);
    
    /**
     * 게시판 전체 조회
     * @return
     */
    List<Board> selectBoardList();
    
    /**
     * 게시판 수정
     * @param board
     */
    void updateBoard(Board board);
    
    /**
     * 게시판 삭제
     * @param uid
     */
    void deleteBoard(String uid);
    

}
