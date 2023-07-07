package com.adnstyle.dalnu_gw.domain;

import lombok.Getter;

@Getter
public class Token {
    
    private String key;
    
    private String value;
    
    private Long expiredTime;
    
    private String username;
    
    private String userEmail;
    
    private String uid;
}
