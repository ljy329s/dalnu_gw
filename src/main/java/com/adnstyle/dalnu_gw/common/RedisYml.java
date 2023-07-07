package com.adnstyle.dalnu_gw.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@ConstructorBinding
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "redis")
public class RedisYml {
    
    private final String host;
    
    private final Integer port;
    
    private final String reKey;
    
    private final String roleKey;
    
}
