package com.qq.client.tools;

import java.util.HashMap;

/**
 *  管理客户端与服务器保持通信的线程类
 */

public class ServerThreadManager {

    private static HashMap<String, ClientToServerThread> map = new HashMap<>();

    public static void addClientToServerThread(String userId, ClientToServerThread serverThread) {
        // 将创建好的ClientToServerThread放入到map
        map.put(userId, serverThread);
    }

    public static ClientToServerThread getClientToServerThread(String userId) {
        // 根据userId取得该线程
        return (ClientToServerThread) map.get(userId);
    }

}
