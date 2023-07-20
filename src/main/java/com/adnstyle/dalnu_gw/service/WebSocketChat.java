package com.adnstyle.dalnu_gw.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * 클라이언트가 접속할때마다 생성되어 클라이언트와 직접 통신하는 클래스
 */
@Slf4j
@Service
@ServerEndpoint(value = "/chat/{roomId}") //websocket을 활성화 시키는 매핑정보를 지정하는 어노테이션
public class WebSocketChat {
    
    private static Set<Session> clients = Collections.synchronizedSet(new HashSet<Session>());//새로운 클라이언트들이 올때마다 저장
    
    public String nowDate() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSS");
        String now = format.format(date);
        return now;
    }
    
    /**
     * 클라이언트가 접속했을때
     * <p>
     * 클라이언트가 ServerEndpoint값인 /chat 으로 접속하게되면 onOpen메서드가 실행되며,
     * 클라이언트 정보를 매개변수인 세션객체를 통해 전달받는다.
     * 이때 정적필드인 clients에 해당 세션이 존재하지 않으면 clients에 접속된 클라이언트를 추가한다.
     */
    
    @OnOpen
    public void onOpen(Session session, @PathParam("roomId") String roomId) {
        log.info("Open Session {} ", session.toString());
        if (!clients.contains(session)) {
            System.out.println("파라미터로 전달된 roomId" + roomId);
            session.getUserProperties().put("roomId", roomId);
            clients.add(session);
            log.info("clients 목록에 새로운 session 추가");
        } else {
            log.info("이미 clients 목록에 연결된 session 입니다 {} ", session);
        }
    }
    
    /**
     * 메시지가 수신됐을때
     * 클라이언트로부터 메시지가 전달되면 onMessage() 메서드에 의해 clients에 있는 모든 세션에 메시지를 전달한다.
     */
    
    @OnMessage
    public void onMessage(String msg, Session session, @PathParam("roomId") String roomId) throws Exception {
        log.info("클라이언트로부터 메시지 전달된 시간 : " + nowDate());
        //동기화 블럭: synchronized 통해 동기화 블럭을 만들면 인자에 대한 작업이 끝나기전까지 그 다음 작업은 대기 상태가 된다.
        synchronized (clients) {
            for (Session s : clients) {
                if (s.getUserProperties().get("roomId").equals(roomId)) {
                    try {
                        s.getBasicRemote().sendText(msg); //채팅메시지를 보내는 메서드
                        System.out.println("msg : " + msg);
                        //레디스에 저장하기
                        //메시지내용, 발신자, 채팅방, 발신시간
                    } catch (IOException e) {
                        log.error("Error sending message : " + e.getMessage());
                    }
                }
            }
        }
    }
    
    /**
     * 클라이언트가 브라우저를 끄거나 다른경로로 이동했을때 세션제거
     *
     * 클라이언트가 url이동을 하거나 브라우저를 종료하면 자동으로 onClose() 메서드가 실행되고
     * 클라이언트 정보를 clients 에서 제거한다.
     */
    @OnClose
    public void onClose(Session session) {
        clients.remove(session);
        log.info("클라이언트 연결종료 session Close : " + nowDate() + " {} ", session);
    }
    
    /**
     * 에러가 발생할 경우 세션제거
     */
    @OnError
    public void onError(Session session, Throwable t) {
        t.printStackTrace();
        clients.remove(session);
        log.info("ERROR 발생 session Close : " + nowDate() + " {} ", session);
    }
}
