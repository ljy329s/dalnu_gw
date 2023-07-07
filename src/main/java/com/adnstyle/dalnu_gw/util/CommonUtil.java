package com.adnstyle.dalnu_gw.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CommonUtil {
    
    /**
     * uuid 랜덤 문자열을 생성하는 메서드
     * @return
     */
    public static String createUUID() {
        String randomUUID = UUID.randomUUID().toString().replace("-", "");
        return randomUUID;
    }

}
