package com.adnstyle.dalnu_gw.service;

import com.adnstyle.dalnu_gw.domain.ChatMessage;
import com.adnstyle.dalnu_gw.domain.ChatRoom;
import com.adnstyle.dalnu_gw.repository.ChatRepository;
import com.adnstyle.dalnu_gw.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {
    
    private final ChatRepository chatRepository;
    
    private final MemberRepository memberRepository;
    
    
    /**
     * 1:1 채팅방 생성
     */
    @Transactional
    public String createSingleChatRoom(String empName, String username) {
        String roomId = new ChatRoom().getRoomId();
        Map<String, String> createChatRoomMap = new HashMap<>();
        createChatRoomMap.put("roomId", roomId);
        createChatRoomMap.put("username", username);
        createChatRoomMap.put("empName", empName);
        createChatRoomMap.put("roomType", "single");
        chatRepository.createSingleChatRoom(createChatRoomMap);
        return roomId;
    }
    
    /**
     * 1:1 채팅방 존재여부 조회, 없다면 생성해서 반환
     */
    
    public String selectSingleChat(String empName, String username) {
        String roomId;
        roomId = chatRepository.selectSingleChat(empName, username);
        //채팅방이 존재하지 않을경우
        if (roomId == null) {
            //새로운 채팅방을 만들고 접근하게하기
            log.info("1:1 채팅방이 존재하지 않습니다 생성후 채팅방에 접근하겠습니다.");
            //로그인한유저와 다른사원의 채팅방을 생성
            roomId = createSingleChatRoom(empName, username);
        }
        log.info("1:1 채팅방 존재 or 생성 채팅방에 접근하겠습니다.");
        return roomId;
        
    }
    
    /**
     * 나와 관련있는 채팅방인지 확인후 관련없는 경우 접근 못함(url변경등)
     */
    public int selectRoom(String roomId) {
        return chatRepository.selectRoom(roomId);
    }
    
    /**
     * 1:1 채팅폼에 필요한 사원목록 리스트 가져오기
     */
    public List<Member> getMemberList() {
        return memberRepository.getMemberList();
    }
    
    /**
     * 로그인한 유저와 관련된 모든 채팅방 조회
     */
    public List<ChatRoom> selectAllChatList(String username) {
        return chatRepository.selectAllChatList(username);
    }
    
    /**
     * db에서 채팅데이터 조회
     */
    public List<ChatMessage> selectChatData(String roomId) {
        return chatRepository.selectChatData(roomId);
    }
    
}
