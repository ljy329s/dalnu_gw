package com.adnstyle.dalnu_gw.controller;

import com.adnstyle.dalnu_gw.domain.ChatMessage;
import com.adnstyle.dalnu_gw.domain.ChatRoom;
import com.adnstyle.dalnu_gw.service.ChatService;
import com.adnstyle.dalnu_gw.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {
    
    private final ChatService chatService;
    
    private final RedisService redisService;
    
    public int start = 0;
    public int end = 9;
    public int all = -1;
    
    /**
     * 사이트 메인화면
     */
    @GetMapping("/")
    public String main() {
        return "main";
    }
    
    /**
     * 채팅방에 접근가능한 권한이 있는지 확인하는 메서드
     * 로그인한 유저와 상대방과 연결되어있는 채팅방이 아닌곳에 접근할수없다.
     */
    @GetMapping("/singleChat/{roomId}")
    public String myChat1(@PathVariable("roomId") String roomId, Model model) throws JsonProcessingException {
        int count = chatService.selectRoom(roomId);
        if (count < 1) {
            model.addAttribute("message", "잘못된 요청입니다.");
            log.info("잘못된 접근");
            return "main";
        }
        model.addAttribute("roomId", roomId);
        List<ChatMessage> chatMessageList = redisService.selectChatList(roomId, start, end);
        model.addAttribute("chatMessageList", chatMessageList);
        return "myChat";
        
    }
    
    /**
     * 로그인한 유저와 다른 사원의 채팅방이 있는지 확인 후 없다면 재생성
     */
    
    @PostMapping("/empChat")
    public String test(@RequestParam("empName") String empName, Model model) throws JsonProcessingException {
        String username = "test"; //토큰에서 꺼내올 로그인한 유저의 아이디 임시로 하드코딩
        //로그인한유저와 다른사원의 채팅방이 있는지 확인
        String roomId = chatService.selectSingleChat(empName, username);
        //채팅데이터 담아서 이동
        List<ChatMessage> chatMessageList = redisService.selectChatList(roomId, start, end);//해당채팅방의 채팅 리스트 가져오기
        model.addAttribute("chatMessageList", chatMessageList);
        return "redirect:/singleChat/" + roomId;
    }
    
    /**
     * 1:1 채팅을 위한 사원목록조회
     */
    @GetMapping("/memberList")
    public String chatForm(Model model) {
        List<Member> memberList = chatService.getMemberList();
        model.addAttribute("memberList", memberList);
        return "memberList";
    }
    
    /**
     * 전체채팅방 목록 조회
     */
    @GetMapping("/chatList")
    public String chatRoomList(Model model) {
        
        String username = "test"; //토큰에서 꺼내올 로그인한 유저의 아이디 (임시로 하드코딩)
        List<ChatRoom> chatRoomList = chatService.selectAllChatList(username);
        model.addAttribute("chatRoomList", chatRoomList);
        return "chatRoomList";
    }
    
    
    /**
     * 전달된 채팅메시지를 레디스에 저장
     */
    @ResponseBody
    @PostMapping("/chatMessage")
    public Map setChatMessageForRedis(@RequestBody String message) throws JSONException, ParseException, JsonProcessingException {
        redisService.setChatMessage(message);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("result", "success");
        return resultMap;
    }
    
    /**
     * 레디스에 저장된 해당 채팅방에 있는 전체 메시지를 가져오기
     */
    @ResponseBody
    @PostMapping("/loadChat")
    public List<ChatMessage> loadChat(@RequestBody String roomId) throws JsonProcessingException {
        List<ChatMessage> redisAllChatList = redisService.selectChatList(roomId, start, all);
        return redisAllChatList;
    }
    
}
