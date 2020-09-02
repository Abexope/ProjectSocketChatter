package com.qq.server.model;

import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.server.tools.ClientThreadManager;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Iterator;

/**
 *  服务器与客户端之间的通信线程
 */

public class ServerToClientThread extends Thread {

    private Socket socket;

    public ServerToClientThread(Socket socket) {
        // 将服务器和该客户端的连接赋值给 socket 成员
        this.socket = socket;
    }

    // 通知其他用户告知自己的在线状态
    public void notifyOther(String selfId) {
        // 获取所有在线客户端的线程
        HashMap<String, ServerToClientThread> map = ClientThreadManager.map;
        Iterator<String> iterator = map.keySet().iterator();
        Message message = new Message();
        message.setMessage(selfId);
        message.setMsgType(MessageType.message_ret_onlineFriend);
        while (iterator.hasNext()) {
            // 取出在线用户ID
            String onlineUserId = iterator.next().toString();   // 由于迭代器的泛型是 String，所以toString多余了
            Socket otherSocket = ClientThreadManager.getClientThread(onlineUserId).socket;
            try {
                ObjectOutputStream oos = new ObjectOutputStream(otherSocket.getOutputStream());
                message.setGetter(onlineUserId);
                oos.writeObject(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            // 该线程可以接收客户端的信息
            try {
                // 服务器接收客户端发来的信息
                ObjectInputStream ois = new ObjectInputStream(this.socket.getInputStream());
                Message message = (Message) ois.readObject();

                // 测试发送端与服务器之间的通信
                /*System.out.println(message.getSendTime() + " |\t" + message.getSender() +
                        " 发给 " + message.getGetter() + ": " + message.getMessage());*/

                /*判断客户端发来的消息类型，然后做相应的处理*/
                if (message.getMsgType().equals(MessageType.message_comm_mes)) {        // 类型3
                    /*消息转发工作*/
                    // 取得接收者的通信线程对象
                    ServerToClientThread getterClientThread = ClientThreadManager.getClientThread(message.getGetter());
                    Socket getterSocket = getterClientThread.socket;    // 接收者的 socket 对象
                    // 创建对象输出流，发送信息
                    ObjectOutputStream oos = new ObjectOutputStream(getterSocket.getOutputStream());
                    oos.writeObject(message);
                } else if (message.getMsgType().equals(MessageType.message_get_onlineFriend)) {

                    /*请求在线好友列表*/
                    // 把在服务器的好友给客户端返回
                    String result = ClientThreadManager.getAllOnlineUserId();
                    Message requestMsg = new Message();
                    requestMsg.setMsgType(MessageType.message_ret_onlineFriend);
                    requestMsg.setMessage(result);
                    requestMsg.setGetter(message.getSender());

                    // 取得消息发送者的通信线程对象
                    ServerToClientThread getterClientThread = ClientThreadManager.getClientThread(message.getSender());
                    Socket senderSocket = getterClientThread.socket;    // 接收者的 socket 对象
                    ObjectOutputStream oos = new ObjectOutputStream(senderSocket.getOutputStream());
                    oos.writeObject(requestMsg);
                }



            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
