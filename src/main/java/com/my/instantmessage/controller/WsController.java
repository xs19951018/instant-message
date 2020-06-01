package com.my.instantmessage.controller;


import com.my.instantmessage.websocket.WebSocketServer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * webSocket接口类
 */
@Controller
@RequestMapping("/public/socket")
public class WsController {

    @RequestMapping("/kill")
    @ResponseBody
    public String ceshi(){
        int onlineCount = WebSocketServer.getOnlineCount();
        return String.valueOf(onlineCount);
    }

    @RequestMapping("/resetByBm")
    @ResponseBody
    public String resetByBm(@RequestParam("userId") String userId){
        int count = WebSocketServer.clearSessionById(userId);
        return String.valueOf(count);
    }

    @RequestMapping("/resetAll")
    @ResponseBody
    public String resetAll(){
        int count = WebSocketServer.clearSessionAll();
        return String.valueOf(count);
    }

    /**
     * 返回信息提示页面
     */
    @RequestMapping("/goMsg")
    public String goMsg(@RequestParam("msg") String msg,
                        Model model){
        System.out.println(msg);
        model.addAttribute("msg", msg);
        return "websocket/error";
    }
}
