package com.adnstyle.dalnu_gw.repository;

import com.adnstyle.dalnu_gw.domain.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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
    
    
    /**
     * 전체직원 조회
     * @return
     */
    List<java.lang.reflect.Member> getMemberList();
}
