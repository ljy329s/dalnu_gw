package com.adnstyle.dalnu_gw.repository;

import com.adnstyle.dalnu_gw.domain.ChatMessage;
import com.adnstyle.dalnu_gw.domain.ChatRoom;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ChatRepository {
    
    void createChatRoom(Map createChatRoomMap);
    
    /**
     * 로그인한 유저의 생성된 모든 채팅방 조회
     */
    List<ChatRoom> selectAllChatList(String username);
    
    int selectRoom(String roomId);
    
    
    /**
     * 1:1 채팅 조회하기
     */
    
    String selectSingleChat(@Param("empName") String empName,@Param("username") String username);
    
    
    /**
     * 1:1 채팅방 생성하기
     */
    void createSingleChatRoom(Map<String, String> createChatRoomMap);
    
    /**
     * 레디스에 저장되어있던 데이터를 db에저장
     */
    void redisToDB(List<ChatMessage> chatMessageList);
    
    /**
     * 채팅방에 이동하기전 데이터를 조회
     */
    List<ChatMessage> selectChatData(String roomId);
}
