package com.adnstyle.dalnu_gw.jwt;

import com.adnstyle.dalnu_gw.common.JwtYml;
import com.adnstyle.dalnu_gw.service.RedisService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;

import static com.auth0.jwt.JWT.require;

/**
 * 토큰과 관련된 작업을 하는 클래스
 */

@Slf4j
@Component
@RequiredArgsConstructor
@Service
public class TokenProvider {
    private final JwtYml jwtYml;
    
    private final RedisService redisService;
    
    
    /**
     * 엑세스 토큰을 생성하는 메서드
     */
    
    @Transactional
    public String createToken(String username, HttpServletResponse response) {
        String token = JWT.create()
            .withIssuer("ljy")
            .withSubject("Jwt_accessToken")
            .withExpiresAt(new Date(System.currentTimeMillis() + jwtYml.getAccessTime()))//만료시간 2분
            .withClaim("username", username)
            .sign(Algorithm.HMAC256(jwtYml.getSecretKey()));
        
        Cookie cookie = new Cookie(jwtYml.getHeader(), jwtYml.getPrefix() + token);
        cookie.setHttpOnly(true);//스크립트 상에서 접근이 불가능하도록 한다.
        //cookie.setSecure(true);//패킷감청을 막기 위해서 https 통신시에만 해당 쿠키를 사용하도록 한다.
        cookie.setPath("/");//쿠키경로 설정 모든경로에서 "/" 사용하겠다
        cookie.setMaxAge(60 * 60 * 24);//쿠키 만료시간 하루 (쿠키에 만료시간을 넣어두지 않으면 창닫기와 동시에 삭제된다.)
        response.addCookie(cookie);
        return token;
    }
    
    public boolean notIssuer(String acToken) {// 잘못된 작성자 token에서 issuer이 없거나, ljy가 아니라면 리턴
        String accToken = acToken.replace(jwtYml.getPrefix(), "");
        String issuer = null;
        try {
            issuer = require(Algorithm.HMAC256(jwtYml.getSecretKey()))
                .build()
                .verify(accToken)
                .getIssuer();
        } catch (NullPointerException e) {
            log.info("토큰의 작성자 없음.");
        } catch (TokenExpiredException e) {
            log.info("토큰 만료");
        }
        if (issuer == null || issuer.equals(jwtYml.getIssuer())) {
            log.info("토큰의 작성자 정상");
            return false;
        }
        return true;
    }
    
    
    /**
     * 리프레시 토큰을 생성하는 메서드
     */
    @Transactional
    public void refreshToken(String username) {//PrincipalDetails principal
        String refKey = username;
        String refToken = JWT.create()
            .withSubject("Jwt_refreshToken")
            .withExpiresAt(new Date(System.currentTimeMillis() + jwtYml.getRefreshTime()))//만료시간 6분
            .sign(Algorithm.HMAC256(jwtYml.getSecretKey()));
        redisService.setRefreshToken(refKey, refToken, jwtYml.getRefreshTime());
    }
    
    /**
     * 엑세스토큰 만료여부를 확인하는 메서드
     */
    public boolean isExpiredAccToken(String jwtToken) {
        String npToken = jwtToken.replace(jwtYml.getPrefix(), "");//prefix 제거
        Date now = new Date();
        Date expiresAt = new Date();
        try {
            expiresAt = require(Algorithm.HMAC256(jwtYml.getSecretKey()))
                .build()
                .verify(npToken)
                .getExpiresAt();
        } catch (TokenExpiredException e) {
            log.info("엑세스토큰 만료");
            return true;
        }
        if (now.before(expiresAt)) {//현재시간이 만료시간보다 이전이라면
            log.info("엑세스토큰 만료전! 만료시간 : " + expiresAt);
            return false;
        }
        return true;
        
    }
    
    /**
     * 리프레시 토큰의 만료여부를 확인하는 토큰 레디스에 존재하면 아직 만료기간 안지난거니까
     */
    
    public boolean isExpiredRefToken(String username, HttpServletResponse response) {
        
        if (redisService.getRefreshToken(username) == null) {//리프레시 토큰이 만료라면
            log.info("리프레시토큰이 만료됐습니다");
            return true;
        }
        
        if (checkRefreshToken(username)) {//리프레시 토큰의 만료기간 확인 7일전이라면
            log.info("리프레시토큰 만료 7일전");
            refreshToken(username);//리프레시 토큰 생성
        }
        
        createToken(username, response); //7일이상이면 엑세스토큰만 생성
        log.info("엑세스토큰 재생성");
        return false;
    }
    
    /**
     * 리프레시 토큰의 만료기간을 확인하는 메서드 7일이내 만료 여부
     */
    
    public boolean checkRefreshToken(String username) {
        try {
            String token = redisService.getRefreshToken(username);
            Date expiresAt = JWT.require(Algorithm.HMAC256(jwtYml.getSecretKey()))
                .build()
                .verify(token)
                .getExpiresAt();
            
            Date current = new Date(System.currentTimeMillis());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(current);
            calendar.add(Calendar.MILLISECOND, jwtYml.getRefreshAlarm());//임시 2분.  7일 남았을때로 해주기 변경 Calendar.DATE
            Date after7dayFromToday = calendar.getTime();
            log.info("리프레시 만료 기간" + expiresAt);
            
            //7일이내 만료
            if (expiresAt.before(after7dayFromToday)) {
                log.info("리프레시토큰 7일이내 만료");
                return true;
            }
        } catch (TokenExpiredException e) {
            return false;
        }
        return false;
        
    }
    
    /**
     * 쿠키에서 토큰을 꺼내는 메서드 쿠키에 저장된 엑세스토큰을 리턴한다
     */
    public String getTokenFromCookie() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {//쿠키가 존재한다면
            for (Cookie c : cookies) {
                if (c.getName().equals("Authorization") && c != null) {//쿠키중에 이름이  Authorization인것만 가져오기 + 비어있지 않을때
                    String acToken = c.getValue();//쿠키에 저장된 엑세스토큰
                    return acToken;
                }
            }
        }
        log.info("쿠키가 존재하지 않습니다");
        return null;
    }
    
    
    /**
     * token을 디코드 하여 username을 반환하는 메서드
     */
    
    public String getNameFromToken(String token) {
        
        String npToken = token.replace(jwtYml.getPrefix(), "");
        if (token != null) {
            DecodedJWT decodedJwt = JWT.decode(npToken);//decode() 메서드는 토큰의 만료를 확인하지 않기에 예외 발생하지 않음.
            String username = decodedJwt.getClaim("username").asString();
            return username;
        }
        return null;
    }
}
