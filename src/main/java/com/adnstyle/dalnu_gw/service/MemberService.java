package com.adnstyle.dalnu_gw.service;

import com.adnstyle.dalnu_gw.domain.Member;
import com.adnstyle.dalnu_gw.enums.TableType;
import com.adnstyle.dalnu_gw.repository.AttachRepository;
import com.adnstyle.dalnu_gw.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Service
public class MemberService {
    
    private final MemberRepository memberRepository;
    private final AttachService attachService;
    /**
     * 직원등록
     * @param member
     * @param uploadFile
     */
    public void insertMember(Member member, MultipartFile uploadFile) {
        
        
        //직원 등록
        memberRepository.insertMember(member);
        
        //프로필 사진이 존재한다면 등록
        if(uploadFile!=null){
            attachService.insertAttach(uploadFile,member.getUid(),String.valueOf(TableType.member));
        }

    }
    
    
}
