package com.adnstyle.dalnu_gw.domain;

import com.adnstyle.dalnu_gw.common.Base;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;

@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@Alias("attach")
public class Attach extends Base implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 참조테이블 타입
     */
    private String tableType;
    
    /**
     * 참조테이블 식별자
     */
    private String tableId;
    
    /**
     * 원본 파일명
     */
    private String displayName;
    
    /**
     * 저장 파일명 _ 고유 식별자
     */
    private String savedName;
    
    /**
     * 파일 저장 경로
     */
    private String savedDir;
    
    /**
     * 파일 형식
     */
    private String type;
    
    /**
     * 파일 사이즈
     */
    private String size;
    
    /**
     * 삭제여부
     */
    private String delYn;
    
    /**
     * 파일크기 N: normal , S: small
     */
    private String flag;
    
    /**
     * 내부 정렬 순서
     */
    private int innerOrder;
    
}