package com.qq.server.tools;

import com.qq.server.model.ServerToClientThread;

import java.util.HashMap;
import java.util.Iterator;

/**
 *  客户端线程管理
 */

public class ClientThreadManager {

    // 客户端用户id - 客户端线程对象 映射表
    public static HashMap<String, ServerToClientThread> map = new HashMap<>();

    // 向 HashMap 中添加通信线程
    public static void addClientThread(String userId, ServerToClientThread clientThread) {

        map.put(userId, clientThread);
    }

    // 返回对应客户端ID的通信线程
    public static ServerToClientThread getClientThread(String userId) {
        return (ServerToClientThread) map.get(userId);
        // PS: HashMap同时实现了用户离线检测，如果不在线get()方法返回null
    }

    // 返回当前在线好友
    public static String getAllOnlineUserId() {
        // 使用迭代器完成
        Iterator<String> iterator = map.keySet().iterator();
        // String result = "";
        StringBuilder result = new StringBuilder();
        while (iterator.hasNext()) {
            // result += iterator.next().toString() + " ";
            result.append(iterator.next()).append(" ");     // 加一个空格分隔符
        }
        return result.toString();
    }

}
