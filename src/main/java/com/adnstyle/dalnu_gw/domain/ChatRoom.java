package com.adnstyle.dalnu_gw.domain;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.UUID;

@Alias("chatRoom")
@Getter
@Setter
public class ChatRoom {
    
    private String seq; //시퀀스
    
    private String roomId;//채팅방아이디
    
    private String roomName;//채팅방이름
    
    private String username;// 유저아이디
    
    private String roomType;// 채팅방타입 single, group
    
    
    /**
     * 랜덤한 채팅방 아이디를 생성하는 채팅방 생성자
     */
    public ChatRoom(){
        this.roomId = UUID.randomUUID().toString();
    }
}
