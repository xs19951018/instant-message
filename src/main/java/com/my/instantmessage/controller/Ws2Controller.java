package com.my.instantmessage.controller;


import com.my.instantmessage.model.Message;
import com.my.instantmessage.websocket.WebSocketServer;
import com.my.instantmessage.websocket.WebSocketServerTwo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

/**
 * webSocket接口类,互相发送消息
 */
@Controller
@RequestMapping("/public/socketTwo")
public class Ws2Controller {

    @RequestMapping("/send")
    @ResponseBody
    public void send(@RequestBody Message message) throws IOException {
        WebSocketServerTwo.receiverMessage(message);
    }

    @RequestMapping("/test")
    @ResponseBody
    public String send() throws IOException {
        return "test";
    }

}
