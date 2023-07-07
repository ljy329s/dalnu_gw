package com.adnstyle.dalnu_gw.controller;

import com.adnstyle.dalnu_gw.domain.Member;
import com.adnstyle.dalnu_gw.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@RequestMapping("/apis/member")
public class MemberController {
    
    private final MemberService memberService;
    
    /**
     * 직원등록
     */
    @PostMapping
    public void insertMember(@RequestPart("member") Member member,
                             @RequestPart(value = "uploadFile", required = false) MultipartFile uploadFile) {
        memberService.insertMember(member, uploadFile);
        
    }
}
