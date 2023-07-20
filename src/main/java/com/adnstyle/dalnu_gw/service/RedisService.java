package com.adnstyle.dalnu_gw.service;

import com.adnstyle.dalnu_gw.auth.PrincipalDetails;
import com.adnstyle.dalnu_gw.common.RedisYml;
import com.adnstyle.dalnu_gw.domain.ChatMessage;
import com.adnstyle.dalnu_gw.domain.Member;
import com.adnstyle.dalnu_gw.repository.ChatRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.*;
/**
 * 레디스 서버에서 깨지지않게 보려면 이렇게 시작해야함
 * redis-cli --raw get kr
 * <p>
 * map 형식 조회는 hget map이름 필드명
 * hget test123 name
 */
/**
 * 레디스에 저장할것
 * 유저의 데이터 username을 키로해서 list나 map으로
 * 유저의 권한목록 Role_username을 키로해서
 * 리프레시 토큰 Ref_username
 *
 * 목록을 담는 방법
 * redisTemplate.opsForValue().set("username", "usernameValue");
 * redisTemplate.opsForValue().set("password", "passwordValue");
 */
/**
 * redis (StringRedisTemplate) 를 이용해 key value로 값을 가져온다
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class RedisService {
    private ObjectMapper mapper = new ObjectMapper();
    private final StringRedisTemplate stringRedisTemplate;
    private final RedisYml redisYml;
    
    private final ChatRepository chatRepository;
    
    /**
     * 리프레시 토큰 레디스에 저장
     */
    public void setRefreshToken(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        Duration expireDuration = Duration.ofMillis(duration);//24초
        valueOperations.set(redisYml.getReKey() + key, value, expireDuration);
    }
    
    /**
     * 리프레시 토큰 가져오기
     */
    public String getRefreshToken(String key) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        return valueOperations.get(redisYml.getReKey() + key);
    }
    
    /**
     * 로그인한 유저의 권한을 저장 Role_이라는 prefix를 앞에다가 추가
     */
    
    public void setUserRole(String key, String value, long duration) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        String roles = value.toString();
        Duration expireDuration = Duration.ofMillis(duration);
        String cleanRoles = roles.replaceAll("[ \\[ \\] ]", "");
        valueOperations.set(redisYml.getRoleKey() + key, cleanRoles, expireDuration);
    }
    
    
    /**
     * 로그인한 유저의 권한을 조회
     */
    public Member getUseRole(String key) {
        ValueOperations<String, String> valueOperations = stringRedisTemplate.opsForValue();
        String roles = valueOperations.get(redisYml.getRoleKey() + key);
        log.info("role : " + roles);
        Member member = new Member();
        member.setRoles(roles);
        return member;
    }
    
    
    /**
     * 로그인시 유저의 정보들을 저장
     */
    
    public void setUserDate(String username, PrincipalDetails principal, long accessTime) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        
        Map<String, String> map = new HashMap<>();
        String name = principal.getMember().getName();
        String userPhone = principal.getMember().getUserPhone();
        String userEmail = principal.getMember().getUserEmail();
        
        map.put("name", name);
        map.put("userPhone", userPhone);
        map.put("userEmail", userEmail);
        
        hashOperations.putAll(username, map);
    }
    
    /**
     * 유저의 정보 조회
     */
    public String getUserDate(String username, String filed) {
        HashOperations<String, String, String> hashOperations = stringRedisTemplate.opsForHash();
        String userData = hashOperations.get(username, filed);
        return userData;
    }
    
    /**
     * 삭제하기
     */
    public void deleteData(String key) {
        stringRedisTemplate.delete(key);
    }
    
    //채팅관련
    
    /**
     * 채팅내역 레디스에 저장
     * 저장할때 key는 chat_채팅방id
     */
    public void setChatMessage(String message) throws JSONException, ParseException, JsonProcessingException {
        String chatKey = createJsonToChatKey(message);
        
        stringRedisTemplate.opsForList().leftPush(chatKey, message); //list로 저장
        
        System.out.println("레디스에 넣은 데이터" + message);
    }


    /**
     * String 을 json으로 변환
     */
    public JSONObject stringParse(String msg) throws ParseException, JsonProcessingException, JSONException {
        JsonNode jsonNode = mapper.readTree(msg);
        return new JSONObject(jsonNode.toString());
    }

    /**
     * redis에 저장할 key 이름 설정
     */
    public String createJsonToChatKey(String message) throws ParseException, JSONException, JsonProcessingException {
        JSONObject jsonObject = stringParse(message); //json으로 변경
        String roomId = (String) jsonObject.get("roomId");
        String chatKey = createChatKey(roomId);
        return chatKey;
    }

    /**
     * redis에 저장할 key 이름 설정
     */
    public String createChatKey(String roomId) {
        String chatKey = redisYml.getChatPrefix() + roomId;
        System.out.println("레디스에 저장될 key : " + chatKey);
        return chatKey;
    }

    /**
     * redis에서 채팅 키 가져오기 지금은 keys를 사용했지만 다음엔 scan으로 변경하자
     */
    public Set<String> getKey(String key) {
        key += "*";
        Set<String> keyList = new HashSet<>();

        keyList = stringRedisTemplate.keys(key);
        System.out.println("getKey() " + key);
        System.out.println("keyList? : " + keyList);
        if (keyList.isEmpty() || keyList == null) {
            log.info("키가 존재하지 않습니다.");
            return null;
        }
        return keyList;
    }

    /**
     * 채팅방이동시 레디스에서 데이터 조회해오기
     */
    public List<ChatMessage> selectChatList(String roomId, int start, int end) throws JsonProcessingException {
        String chatKey = createChatKey(roomId);

        List<String> messageList = stringRedisTemplate.opsForList().range(chatKey, start, end);

        List<ChatMessage> chatMessageList = new ArrayList<>();

        //string형식으로 저장된 레디스 데이터를 json으로 변경
        for (String value : messageList) {
            //찾아온 value값으로 dto로 바꾸기
            ChatMessage msg = mapper.readValue(value, ChatMessage.class);
            log.info("============ chatMessage ============= {} ", msg);
            System.out.println("msg : " + msg);
            chatMessageList.add(0, msg);
        }
        return chatMessageList;
    }

    /**
     * 배치작업용 메소드
     */

    public void redisToDB() throws JsonProcessingException {
        Set<String> chatKeys = getKey(redisYml.getChatPrefix());
        List<String> keylist = null;
        try {
            for (String chatKey : chatKeys) {
                Long size = stringRedisTemplate.opsForList().size(chatKey);
                if (size > 20) {
                    log.info("list의 size가 20이상인것 {}", chatKey);
                    //조회된 keylist를 가지고 list를 가져온다 db저장
                    keylist = stringRedisTemplate.opsForList().range(chatKey, 10, -1);
                    //레디스에서 삭제 지정범위를 남기고 다 삭제
                    stringRedisTemplate.opsForList().trim(chatKey, 0, 9);
                }
            }
            //db에 저장하기
            List<ChatMessage> chatMessageList = new ArrayList<>();

            for (String value : keylist) {
                //찾아온 value값으로 dto로 바꾸기
                ChatMessage msg = mapper.readValue(value, ChatMessage.class);
                log.info("============ chatMessage ============= {} ", msg);
                chatMessageList.add(msg);
            }
            chatRepository.redisToDB(chatMessageList);//db에 저장
        } catch (NullPointerException e) {
            log.info(e.getMessage());
        }
    }
    
}






