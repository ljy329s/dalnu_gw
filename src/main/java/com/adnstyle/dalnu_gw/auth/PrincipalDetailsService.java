package com.adnstyle.dalnu_gw.auth;

import com.adnstyle.dalnu_gw.domain.Member;
import com.adnstyle.dalnu_gw.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       log.info("======= loadUserByUsername 시작! 해당유저 존재 여부 확인 ===========");
        Member member = memberRepository.selectMember(username);
        if(member==null){
           log.info("{} 유저가 존재하지 않습니다.",username);
            return null;
        }
        log.info("{} 유저가 존재합니다.",username);
        
        return new PrincipalDetails(member);
    }
}
