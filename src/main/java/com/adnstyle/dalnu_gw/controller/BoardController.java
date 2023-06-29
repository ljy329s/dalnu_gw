package com.adnstyle.dalnu_gw.controller;

import com.adnstyle.dalnu_gw.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class BoardController {
    
    private final BoardService boardService;
}
