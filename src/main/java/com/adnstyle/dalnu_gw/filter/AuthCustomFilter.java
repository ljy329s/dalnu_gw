package com.adnstyle.dalnu_gw.filter;

import com.adnstyle.dalnu_gw.common.JwtYml;
import com.adnstyle.dalnu_gw.jwt.JwtAuthenticationFilter;
import com.adnstyle.dalnu_gw.jwt.JwtAuthorizationFilter;
import com.adnstyle.dalnu_gw.jwt.TokenProvider;
import com.adnstyle.dalnu_gw.service.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.stereotype.Component;

/**
 * 인증처리 커스텀 필터
 */
@Component
@RequiredArgsConstructor
public class AuthCustomFilter extends AbstractHttpConfigurer<AuthCustomFilter, HttpSecurity> {
    private final TokenProvider tokenProvider;
    
    private final JwtYml jwtYml;
    
    private final RedisService redisService;
    
    
    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);//꼭 넘겨야하는 파라미터 AuthenticationManger! 얘가 로그인을 진행하는 필터이기 때문
        http
            .addFilter(new JwtAuthenticationFilter(authenticationManager, jwtYml, tokenProvider,redisService))//로그인 인증처리
            .addFilter(new JwtAuthorizationFilter(authenticationManager,jwtYml, tokenProvider, redisService));//인증 + 인가처리
        
    }
}
