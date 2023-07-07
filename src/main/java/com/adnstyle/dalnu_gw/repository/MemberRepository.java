package com.adnstyle.dalnu_gw.repository;

import com.adnstyle.dalnu_gw.domain.Member;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberRepository {
    /**
     * 직원등록
     * @param member
     */
    void insertMember(Member member);
    
    /**
     * 직원조회
     */
    Member selectMember(String username);
}
