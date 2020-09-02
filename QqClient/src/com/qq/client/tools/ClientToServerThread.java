package com.qq.client.tools;

import com.qq.client.view.QqChat;
import com.qq.client.view.QqFriendList;
import com.qq.common.Message;
import com.qq.common.MessageType;

import java.io.ObjectInputStream;
import java.net.Socket;

/**
 *  客户端与服务器保持连接的线程
 */

public class ClientToServerThread extends Thread{

    private Socket socket;

    public Socket getSocket() {
        return socket;
    }

    public ClientToServerThread(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        while (true) {
            // 不停地监听从服务器发来的信息
            try {
                ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
                Message message = (Message) ois.readObject();
                /*System.out.println("读取到从服务器发来的消息 " + message.getSender() + " 给 " + message.getGetter()
                    + " 内容 " + message.getMessage());*/

                /*根据不同的消息类型执行不同的行为*/
                if (message.getMsgType().equals(MessageType.message_comm_mes)) {    // 普通包
                    // 将服务器获得的消息显示到该显示的聊天界面
                    QqChat qqChat = QqChatManager.getQqChat(
                            message.getGetter() + " " + message.getSender());
                    // 显示消息
                    qqChat.showMessage(message);
                } else if (message.getMsgType().equals(MessageType.message_ret_onlineFriend)) {     // 表示请求获取当前在线好友

                    String onlineMsg = message.getMessage();
                    String[] friends = onlineMsg.split(" ");
                    String getter = message.getGetter();

                    // 修改相应的好友列表
                    QqFriendList qqFriendList = QqFriendListManager.getQqFriendList(getter);
                    // 更新在线好友列表
                    if (qqFriendList != null)
                        qqFriendList.updateFriend(message);
                }
                /*MessageType接口相当于定义了一个socket与server之间的通信协议/规则*/

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
