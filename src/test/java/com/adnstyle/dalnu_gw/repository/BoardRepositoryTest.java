package com.adnstyle.dalnu_gw.repository;

import com.adnstyle.dalnu_gw.domain.Board;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
@Slf4j
public class BoardRepositoryTest {
    
    @Autowired
    private BoardRepository boardRepository;
    
    @Test
    void selectBoard(){
       Board board = boardRepository.selectBoard();
       log.info("board :{}",board);
    }
}
