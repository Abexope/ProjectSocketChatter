package com.qq.client.view;

import com.qq.client.tools.ServerThreadManager;
import com.qq.common.Message;
import com.qq.common.MessageType;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Date;


/**
 *  模拟聊天窗口
 *  由于客户端需要处于时刻监听服务器回传的信息，因此要把它也做成一个线程类
 */

public class QqChat extends JFrame implements ActionListener {

    JTextArea jta;      // 聊天窗口
    JTextField jtf;     // 输入栏
    JButton jb;         // 发送按钮
    JPanel jp;
    String ownerId;     // 自身ID
    String friendId;    // 好友ID

    public QqChat(String ownerID, String friendId) {
        this.ownerId = ownerID;
        this.friendId = friendId;

        jta = new JTextArea();      // 文本域
        jtf = new JTextField(15);   // 输入栏长度
        jb = new JButton("发送");
        jb.addActionListener(this);
        jp = new JPanel();
        jp.add(jtf);
        jp.add(jb);

        this.add(jta, "Center");
        this.add(jp, "South");

        this.setSize(350, 300);
        this.setIconImage((new ImageIcon("./image/qq1.jpg")).getImage());
        this.setTitle(ownerID + " 正在和 " + this.friendId + " 聊天");
        this.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb) {      // 如果用户点击了发送按钮
            // 信息打包
            Message message = new Message();
            message.setSender(this.ownerId);
            message.setGetter(this.friendId);
            message.setMessage(this.jtf.getText());
            message.setSendTime(new Date().toString());
            message.setMsgType(MessageType.message_comm_mes);
            // 发送给服务器
            Socket socket = ServerThreadManager.getClientToServerThread(ownerId).getSocket();
            try {
                ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                oos.writeObject(message);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

    // 定义一个显示消息的方法
    public void showMessage(Message message) {
        // 显示
        String info = message.getSendTime().toString() + " |    " + message.getSender() + " 对 "
                + message.getGetter() + " 说：" + message.getMessage() + "\n";
        this.jta.append(info);
    }

    public static void main(String[] args) {
        QqChat qqChat = new QqChat(
                "葬爱家族#族长", "葬爱家族#01号");
    }

}
