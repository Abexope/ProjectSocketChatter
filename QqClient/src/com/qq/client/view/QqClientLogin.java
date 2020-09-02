package com.qq.client.view;

import com.qq.client.tools.ClientToServerThread;
import com.qq.client.tools.QqFriendListManager;
import com.qq.client.tools.ServerThreadManager;
import com.qq.common.Message;
import com.qq.common.MessageType;
import com.qq.common.User;
import com.qq.client.model.QqClientUser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 *  模拟QQ客户端登陆界面
 */
public class QqClientLogin extends JFrame implements ActionListener {

    // 定义北部需要的组件
    JLabel jbl1;

    // 定义中部需要的组件
    // 中部有3个JPanel，有一个选项卡窗口管理
    JTabbedPane jtp;
    JPanel jp2, jp3, jp4;   // jp2对应账号密码登陆；jp3对应手机号登陆；jp4对应邮箱登陆
    JLabel jp2_jbl1, jp2_jbl2, jp2_jbl3, jp2_jbl4;
    JButton jp2_jb1;
    JTextField jp2_jtf;             // 账号输入
    JPasswordField jp2_jpf;         // 密码输入
    JCheckBox jp2_jcb1, jp2_jcb2;   // 复选框（隐身登录、忘记密码）


    // 定义南部需要的组件
    JPanel jp1;
    JButton jp1_jb1, jp1_jb2, jp1_jb3;

    public QqClientLogin() {

        // 处理北部
        jbl1 = new JLabel(new ImageIcon("./image/tou.gif"));
        this.add(jbl1, "North");        // 将 jbl1 放在北部
        this.setSize(350, 240);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // 处理南部
        jp1 = new JPanel();
        jp1_jb1 = new JButton(new ImageIcon("./image/denglu.gif"));
        // 响应用户点击登陆
        jp1_jb1.addActionListener(this);
        jp1_jb2 = new JButton(new ImageIcon("./image/quxiao.gif"));
        jp1_jb3 = new JButton(new ImageIcon("./image/xiangdao.gif"));
        // 将三个按钮放入jp1
        jp1.add(jp1_jb1);
        jp1.add(jp1_jb2);
        jp1.add(jp1_jb3);
        this.add(jp1, "South");         // 将 jp1 放在南部

        // 处理中间
        // QQ号码登陆窗口
        jp2 = new JPanel(new GridLayout(3, 3));
        jp2_jbl1 = new JLabel("QQ号码", JLabel.CENTER);
        jp2_jbl2 = new JLabel("QQ密码", JLabel.CENTER);
        jp2_jbl3 = new JLabel("忘记密码", JLabel.CENTER);
        jp2_jbl3.setForeground(Color.BLUE);     // 修改文字颜色
        jp2_jbl4 = new JLabel("申请密码保护", JLabel.CENTER);
        jp2_jb1 = new JButton(new ImageIcon("./image/clear.gif"));
        jp2_jtf = new JTextField();
        jp2_jpf = new JPasswordField();
        jp2_jcb1 = new JCheckBox("隐身登录");
        jp2_jcb2 = new JCheckBox("记住密码");
        //将组件按顺序加入到jp2
        jp2.add(jp2_jbl1);
        jp2.add(jp2_jtf);
        jp2.add(jp2_jb1);
        jp2.add(jp2_jbl2);
        jp2.add(jp2_jpf);
        jp2.add(jp2_jbl3);
        jp2.add(jp2_jcb1);
        jp2.add(jp2_jcb2);
        jp2.add(jp2_jbl4);
        // 其他窗口
        jp3 = new JPanel(new GridLayout(3, 3));     // 手机登录
        jp4 = new JPanel(new GridLayout(3, 3));     // 邮箱登陆

        // 创建选项卡窗口
        jtp = new JTabbedPane();
        jtp.add("QQ号码", jp2);
        jtp.add("手机号码", jp3);
        jtp.add("电子邮件", jp4);

        // 将选项卡窗口加入中部界面
        this.add(jtp, "Center");
        // 修改窗口LOGO
        this.setIconImage((new ImageIcon("./image/qq1.jpg")).getImage());


        this.setVisible(true);      // 界面可视化
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 如果用户点击登陆
        if (e.getSource() == jp1_jb1) {
            QqClientUser qqClientUser = new QqClientUser();
            User user = new User();
            user.setUserId(jp2_jtf.getText().trim());           // 客户端接口外部输入设备输入
            user.setPassWd(new String(jp2_jpf.getPassword()));  // 客户端接口外部输入设备输入

            if (qqClientUser.checkUser(user)) {     // 验证通过

                try {

                    // 打开好友列表，同时将该客户端的好友列表对象保存到服务器中的HashMap中
                    QqFriendList qqFriendList = new QqFriendList(user.getUserId());
                    QqFriendListManager.addQqFriendList(user.getUserId(), qqFriendList);

                    /*发送一个要求返回在线好友的请求包*/
                    // 制作一个请求包
                    Message requestMsg = new Message();     // 表示请求在线好友的信息包
                    requestMsg.setMsgType(MessageType.message_get_onlineFriend);    // 消息类型
                    requestMsg.setSender(user.getUserId());     // 指明客户端身份
                    // 获取当前用户的 socket 对象
                    Socket socket = ServerThreadManager.getClientToServerThread(user.getUserId()).getSocket();
                    ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
                    oos.writeObject(requestMsg);    // 客户端向服务器发送获取在线好友的请求

                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                // 关闭登陆界面
                this.dispose();
            } else {    // 验证失败，返回失败信息
                JOptionPane.showMessageDialog(this, "用户名或密码错误");
            }

        }
    }

    public static void main(String[] args) {
        QqClientLogin qqClientLogin = new QqClientLogin();
    }

}
