package com.my.instantmessage.enums;

import lombok.Getter;

/**
 * 自定义消息枚举
 */
@Getter
public enum MessageEnum {

    SIMPLE_MSG(1, "普通消息"),
    ALL_MSG(2, "群发消息")
    ;

    private Integer code;
    private String message;

    MessageEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
