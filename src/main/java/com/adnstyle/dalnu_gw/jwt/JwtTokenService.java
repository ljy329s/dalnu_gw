package com.adnstyle.dalnu_gw.jwt;

import com.adnstyle.dalnu_gw.common.JwtYml;
import com.adnstyle.dalnu_gw.domain.Token;
import com.adnstyle.dalnu_gw.repository.MemberRepository;
import com.adnstyle.dalnu_gw.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
 * 토큰의 검증, 발급을 담당할 클래스
 */
@Service
@RequiredArgsConstructor
public class JwtTokenService {
    private final JwtYml jwtYml;
    private final MemberRepository memberRepository;
    private final RedisService redisService;
    
    private final TokenProvider tokenProvider;
    
    @Transactional
    public String reissue(Token token, HttpServletRequest request) {
        String key = token.getUsername();
    
        if (tokenProvider.isExpiredRefToken(key)) {//리프레시 토큰 만료 여부 확인
            String jwtToken = request.getHeader(jwtYml.getHeader()).replace(jwtYml.getPrefix() + " ", "");
            String accToken = tokenProvider.reCreateAccToken(jwtToken);
            return accToken;
        }else {
            return null;
        }
    
    }
    
}
