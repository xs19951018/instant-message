package com.my.instantmessage.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.my.instantmessage.enums.MessageEnum;
import com.my.instantmessage.model.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@ServerEndpoint(value = "/public/WebSocketServerTwo/{userId}")
public class WebSocketServerTwo {
    
    private static Logger log = LoggerFactory.getLogger(WebSocketServerTwo.class);

    // 静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static final AtomicInteger onlineCount = new AtomicInteger(0);
    // concurrent包的线程安全map，用来存放每个客户端对应的WebSocketServerTwo对象。
    private static Map<String, WebSocketServerTwo> clients = new ConcurrentHashMap<String, WebSocketServerTwo>(30);
    // 与某个客户端的连接会话，需要通过它来给客户端发送数据
    private String userId;
    private Session session;

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(@PathParam("userId")String userId, Session session) {
        log.info("新客户端连入，用户id：" + userId);
        this.userId = userId;
        this.session = session;

        if(clients.get(userId) == null){
            addOnlineCount();           // 在线数加1
        }
        clients.put(userId, this);  // 加入set中
        String msg = userId+"连接成功,"+"当前在线人数为："+getOnlineCount();
        sendMessage(msg);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        log.info("一个客户端关闭连接");
        if(clients.get(userId) != null){
            subOnlineCount();   // 在线数减1
        }
        clients.remove(userId); // 从set中删除
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户发送过来的消息为："+message);
    }

    /**
     * 发生错误时调用
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("websocket出现错误");
        error.printStackTrace();
    }

    /**
     * 发送消息
     */
    private void sendMessage(String message) {
        try {
            // 根据消息发送人调用对应的session
            this.session.getBasicRemote().sendText(message);
            log.info("发送消息成功，消息为：" + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送消息 
     */
    private static void sendMessage(Message message) {
        try {
            // 根据消息发送人调用对应的session
            clients.get(message.getReceiver()).session.getBasicRemote()
                    .sendText(message.getContent());
            log.info("发送消息成功，消息为：" + message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 群发消息
     */
    private static void sendAll(String message) throws IOException {
        for (WebSocketServerTwo webSocketServerTwo : clients.values()) {
            webSocketServerTwo.sendMessage(message);
        }
    }

    /**
     * 发送消息全局调用(包装消息类)
     * @param message
     */
    public static void receiverMessage(Message message) throws IOException {
        if(message != null){
            if(MessageEnum.SIMPLE_MSG.getCode().equals(message.getType())){
                //普通消息
                sendMessage(message);
            }else if(MessageEnum.ALL_MSG.getCode().equals(message.getType())){
                //群发消息
                sendAll(message.getContent());
            }
        }
    }

    public static synchronized int getOnlineCount() {
        return onlineCount.get();
    }

    private static synchronized void addOnlineCount() {
        onlineCount.incrementAndGet(); // 在线数+1
    }

    private static synchronized void subOnlineCount() {
        onlineCount.decrementAndGet(); // 在线数-1
    }
}