package com.qq.client.tools;

import com.qq.client.view.QqFriendList;

import java.util.HashMap;

/**
 *  管理好友、黑名单、界面类
 */

public class QqFriendListManager {

    private static HashMap<String, QqFriendList> map = new HashMap<>();

    public static void addQqFriendList(String userId, QqFriendList qqFriendList) {
        map.put(userId, qqFriendList);
    }

    public static QqFriendList getQqFriendList(String userId) {
        return (QqFriendList) map.get(userId);
    }

}
