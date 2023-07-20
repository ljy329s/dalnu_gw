package com.adnstyle.dalnu_gw.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * @ServerEndpoint 어노테이션이 달린 클래스들은 websocket이 생성될때마다 인스턴스가 생성되고
 * JWA에 의해 관리되기 때문에 @Autowrired가 설정된 멤버들이 정상적으로 초기화 되지 않는 문제 발생
 * 이때 연결해주고 초기화해주는 클래스
 */
@Configuration//설정클래스
public class WebSocketConfig {
    
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
        
    }
}

