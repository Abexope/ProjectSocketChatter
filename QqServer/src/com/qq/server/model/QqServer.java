package com.qq.server.model;

import com.qq.common.Message;
import com.qq.common.User;
import com.qq.server.tools.ClientThreadManager;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *  服务器后台
 *      持续监听，等待客户端连接
 */
public class QqServer {

    public QqServer() {
        try {
            // 在 9999 监听
            System.out.println("Server：在9999监听");
            ServerSocket server = new ServerSocket(9999);

            while (true) {
                // 阻塞，等待客户端连接
                Socket socket = server.accept();

                // 服务器接收客户端发来的信息
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                User user = (User) ois.readObject();

                System.out.println("Server：服务器接收到用户ID " + user.getUserId() + "\t密码：" + user.getPassWd());

                // 基础验证：只要密码是 "123456" 就OK，同时向客户端回传信息【真实场景需要查询数据库】
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                Message message = new Message();
                if (user.getPassWd().equals("123456")) {
                    // 服务器向客户端回传登陆成功信息
                    message.setMsgType("1");    // 成功
                    oos.writeObject(message);

                    // 实例化一个客户端与服务器之间保持连接的线程对象
                    ServerToClientThread clientThread = new ServerToClientThread(socket);

                    // 将该线程对象加入到线程管理者对象中（低并发场景下使用静态形式尚可）
                    ClientThreadManager.addClientThread(user.getUserId(), clientThread);

                    // 启动线程，让该线程与客户端保持通信
                    clientThread.start();

                    // 通知其它在线用户
                    clientThread.notifyOther(user.getUserId());

                } else {
                    // 服务器向客户端回传登陆失败信息
                    message.setMsgType("2");    // 失败
                    oos.writeObject(message);
                    socket.close();     // 关闭连接
                }

                System.out.println("Server：服务器向用户 " + user.getUserId() + " 回传信息");
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
