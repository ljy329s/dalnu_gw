package com.adnstyle.dalnu_gw.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

/**
 * 채팅 메시지
 */

@Alias("chatMessage")
@Getter
@Setter
public class ChatMessage {
    
    /**
     * 채팅메시지 식별값
     */
    private String seq;
    
    /**
     * 채팅메시지 내용
     */
    private String message;
    
    /**
     * 채팅발신자
     */
    private String username;
    
    /**
     * 발신일자
     */
    private String sendDate;
    
    /**
     * 채팅방 아이디
     */
    private String roomId;
    
    /**
     * 채팅메시지 전송(밀리세컨드)
     */
    private Long timeStamp;
}
