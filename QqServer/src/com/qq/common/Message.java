package com.qq.common;

import java.io.Serializable;

/**
 *  消息承载类
 *
 *  消息类型代码
 *      1：登陆成功
 *      2：登陆失败
 *      3：普通消息包
 */

public class Message implements Serializable {

    private static final long serialVersionUID = 420L;

    private String msgType;     // 消息类型
    private String sender;      // 发送者
    private String getter;      // 接收者
    private String message;     // 消息内容
    private String sendTime;    // 发送时间

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGetter() {
        return getter;
    }

    public void setGetter(String getter) {
        this.getter = getter;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

}
