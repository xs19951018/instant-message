package com.my.instantmessage.model;

import lombok.Data;

/**
 * webSocet通信消息类
 */
@Data
public class Message {

    /** 类型:1普通消息2群发消息 */
    private Integer type;
    /** 发送者 */
    private String sender;
    /** 接收者 */
    private String receiver;
    /** 消息内容 */
    private String content;

}
