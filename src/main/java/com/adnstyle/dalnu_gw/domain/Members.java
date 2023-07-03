package com.adnstyle.dalnu_gw.domain;

import com.adnstyle.dalnu_gw.common.Base;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@SuperBuilder(toBuilder = true)
@Getter
@ToString(callSuper = true)
@NoArgsConstructor
@Alias("members")
public class Members extends Base implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 식별자
     */
    private String uid;
    
    /**
     * 직원 아이디 겸 이메일
     */
    private String userEmail;
    
    /**
     * 직원 비밀번호
     */
    private String password;
    
    /**
     * 직원이름
     */
    private String name;
    
    /**
     * 직원휴대전화
     */
    private String userPhone;
    
    /**
     * 직원생일
     */
    private Date userBirth;
    
    /**
     * 퇴사여부
     */
    private String delYn;
    
    /**
     * 권한
     */
    private String roles;
    
    /**
     * 입사일자
     */
    private LocalDateTime employDate;
    
    /**
     * 퇴사일자
     */
    private LocalDateTime leaveDate;
    
    /**
     * 사내번호
     */
    private String companyTelNumber;
    
    /**
     * 부서명
     */
    private String dept;
    
    /**
     * 직급명
     */
    private String rank;
    
}
