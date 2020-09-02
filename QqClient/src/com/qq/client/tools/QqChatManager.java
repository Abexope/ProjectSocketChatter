package com.qq.client.tools;

import com.qq.client.view.QqChat;

import java.util.HashMap;

/**
 *  管理用户聊天界面的类
 */

public class QqChatManager {

    private static HashMap<String, QqChat> map = new HashMap<>();

    // 加入
    public static void addQqChat(String loginIdAnFriendId, QqChat qqChat) {
        map.put(loginIdAnFriendId, qqChat);
    }

    // 取出
    public static QqChat getQqChat(String loginInAnFriendId) {
        return (QqChat) map.get(loginInAnFriendId);
    }

}
