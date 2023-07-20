package com.adnstyle.dalnu_gw.domain;

import com.adnstyle.dalnu_gw.common.Base;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@Alias("member")
public class Member extends Base implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    /**
     * 식별자
     */
    private String uid;
    
    /**
     * 직원 아이디
     */
    private String username;
    
    /**
     * 직원 비밀번호
     */
    private String password;
    
    /**
     * 직원 이메일
     */
    private String userEmail;
    
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
    private Date employDate;
    
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
    
    /**
     * roles 컬럼에 ROLE_ADMIN,ROLE_USER 등 여러개가 들어있을때 콤마를 기준으로 하여 가져오는 메서드
     * 만약 리스트가 없을경우에는 빈 ArrayList 를 리턴한다
     */
    public List<String> getRoleList(){
        if(this.roles.length()>0) {
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }
    
    
}
