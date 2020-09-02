package com.qq.client.model;

import com.qq.client.tools.ClientToServerThread;
import com.qq.client.tools.ServerThreadManager;
import com.qq.common.Message;
import com.qq.common.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *  客户端后台，访问服务器
 */

public class QqClientConnectServer {

    public Socket socket;    // 客户端发送数据方案一：静态socket（并不妥当，导致前后端耦合）

    // 发送第一次请求
    public boolean sendLoginInfoToServer(Object o) {
        boolean b = false;
        try {
            socket = new Socket("127.0.0.1", 9999);
            System.out.println("Client：实例化Socket");

            // 客户端向服务器发送信息
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject(o);
            System.out.println("Client：已向服务器发送信息");

            // 客户端接收服务器回传的信息
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            Message ms = (Message)ois.readObject();

            //b = ms.getMsgType().equals("1");
            // 验证用户登录
            if (ms.getMsgType().equals("1")) {
                // 创建一个该QQ号与服务器保持通讯的线程
                ClientToServerThread serverThread = new ClientToServerThread(socket);
                serverThread.start();   // 启动线程
                ServerThreadManager.addClientToServerThread(((User) o).getUserId(), serverThread);
                b = true;
            }



            System.out.println("Client：已接收到服务器的信息");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return b;
    }

    public void sendInfoToServer(Object o) {
        try {
            Socket s = new Socket("127.0.0.1", 9999);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
