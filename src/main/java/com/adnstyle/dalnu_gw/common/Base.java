package com.adnstyle.dalnu_gw.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * 예외발생하지 않는다면 모든 도메인이 상속받을 기본 도메인
 */
@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
public class Base {
    
//    @JsonIgnore
//    private Map<String, Object> _base;
    
    /**
     * 식별자
     */
    private String uid;
    
    /**
     * 생성자
     */
    private String createdBy;
    
    /**
     * 생성일
     */
    private LocalDateTime createDate;
    
    /**
     * 수정일
     */
    private LocalDateTime modifyDate;
    
    /**
     * 삭제여부
     */
    private String delYn;
    
    
}
