package com.adnstyle.dalnu_gw.repository;

import com.adnstyle.dalnu_gw.domain.Board;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
public class BoardRepositoryTest {
    
    @Autowired
    private BoardRepository boardRepository;
    
    @DisplayName("게시판 상세 조회")
    @Test
    void selectBoard() {
        String uid = "22550670-1669-11ee-9716-020009ab006d";
        Board board = boardRepository.selectBoard(uid);
        log.info("board :{}", board);
    }
}
