package com.adnstyle.dalnu_gw.repository;

import com.adnstyle.dalnu_gw.domain.Board;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardRepository {
    
    Board selectBoard();
    
    /**
     * 게시판 글 등록
     * @param board
     */
    void insertBoard(Board board);
}
