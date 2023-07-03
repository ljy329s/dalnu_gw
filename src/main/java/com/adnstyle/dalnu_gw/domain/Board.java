package com.adnstyle.dalnu_gw.domain;

import com.adnstyle.dalnu_gw.common.Base;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.List;

/**
 * 게시판
 */
@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@Alias("board")
public class Board extends Base implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 제목
     */
    private String title;
    
    /**
     * 내용
     */
    private String contents;
    
    /**
     * 조회수
     */
    private String viewCount;
    
    /**
     * 게시판 종류
     */
    private String boardType;
    
    /**
     * 첨부파일회
     */
    private List<String> attachList;
}
