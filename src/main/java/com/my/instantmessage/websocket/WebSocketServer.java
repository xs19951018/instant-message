package com.my.instantmessage.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.ContextLoader;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
@ServerEndpoint("/public/socketServer/{userId}")
public class WebSocketServer {

    private static int onlineCount = 0;
    private static Map<String, WebSocketServer> clients = new ConcurrentHashMap<String, WebSocketServer>(300);
    private Session session;
    private String userId;
    private String activityId;
    private boolean isZt = false;    //是主体单位

    // 注入接口,不能使用@Autowire注解
    /*private IBxgcPjResultService pjResultService = (IBxgcPjResultService) ContextLoader
            .getCurrentWebApplicationContext().getBean("bxgcPjResultService");*/

    @OnOpen
    public synchronized void onOpen(@PathParam("userId") String userId,
                                    Session session) throws IOException {
        this.activityId = activityId;
        this.userId = userId;
        this.session = session;

        //判断是否存在此id
        if(clients.get(userId) != null){
            session.getAsyncRemote().sendText("1");
        }else{
            isZt = true;
            addOnlineCount();
            clients.put(userId, this);
            System.out.println("已连接");
            session.getAsyncRemote().sendText("0");
        }
    }

    @OnClose
    public void onClose() throws IOException {
        System.out.println("关闭了.........");
        //是主体单位关闭才会清除会话
        if(isZt){
            clients.remove(userId);
            subOnlineCount();
        }
    }

    @OnMessage
    public void onMessage(String message) throws IOException {
        System.out.println(message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    public static synchronized int clearSessionById(String userId) {
        //遍历找到指定人的会话,向客户端发送消息(该二维码已被重置!)
        for (WebSocketServer item : clients.values()) {
            if (item.userId.equals(userId) ) {
                item.isZt = true;
                item.session.getAsyncRemote().sendText("2");
            }
        }
        return 1;
    }

    public static synchronized int clearSessionAll() {
        //清除所有session
        for (WebSocketServer item : clients.values()) {
            item.isZt = true;
            item.session.getAsyncRemote().sendText("2");
        }
        return 1;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    public static synchronized Map<String, WebSocketServer> getClients() {
        return clients;
    }
}
