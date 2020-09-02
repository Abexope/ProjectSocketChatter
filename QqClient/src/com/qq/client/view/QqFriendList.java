package com.qq.client.view;

import com.qq.client.tools.QqChatManager;
import com.qq.common.Message;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *  模拟QQ好友列表
 */

public class QqFriendList extends JFrame implements ActionListener, MouseListener {

    // 处理第一张卡片：好友
    JPanel jphy1, jphy2, jphy3;
    JButton jphy_jb1, jphy_jb2, jphy_jb3;
    JScrollPane jsp1;

    // 处理第二张卡片：默生人
    JPanel jpmsr1, jpmsr2, jpmsr3;
    JButton jpmsr_jb1, jpmsr_jb2, jpmsr_jb3;
    JScrollPane jsp2;

    // 好友列表
    JLabel[] jbls;

    // 将整个JFrame设置成CardLayout
    CardLayout cl;

    // 客户端成员属性
    private String ownerId;

    public QqFriendList(String ownerId) {

        this.ownerId = ownerId;

        jphy_jb1 = new JButton("我的好友");
        jphy_jb2 = new JButton("陌生人");
        jphy_jb2.addActionListener(this);       // 监听接口
        jphy_jb3 = new JButton("黑名单");

        /*处理第一张卡片：好友列表*/
        jphy1 = new JPanel(new BorderLayout());

        // 假设有50个好友
        jphy2 = new JPanel(new GridLayout(50, 1, 4, 4));
        // 给jphy2初始化50个好友
        jbls = new JLabel[50];
        for (int i = 0; i < 50; i++) {
            jbls[i] = new JLabel(i+1+"", new ImageIcon("./image/mm.jpg"), JLabel.LEFT);

            jbls[i].setEnabled(false);      // 默认是灰色头像
            if (jbls[i].getText().equals(ownerId)) {
                jbls[i].setEnabled(true);   // 转化为彩色头像
            }

            // 添加监听，当鼠标点击头像时变成高亮状态
            jbls[i].addMouseListener(this);
            jphy2.add(jbls[i]);

        }

        jphy3 = new JPanel(new GridLayout(2, 1));
        // 将下面两个按钮加入到jphy3中
        jphy3.add(jphy_jb2);
        jphy3.add(jphy_jb3);

        jsp1 = new JScrollPane(jphy2);

        // 对 jphy1 初始化
        jphy1.add(jphy_jb1, "North");
        jphy1.add(jsp1, "Center");
        jphy1.add(jphy3, "South");


        /*处理第二长卡片：陌生人*/
        jpmsr_jb1 = new JButton("我的好友");
        jpmsr_jb1.addActionListener(this);      // 监听接口
        jpmsr_jb2 = new JButton("陌生人");
        jpmsr_jb3 = new JButton("黑名单");

        // 处理第一张卡片：好友列表
        jpmsr1 = new JPanel(new BorderLayout());

        // 假设有20个陌生人
        jpmsr2 = new JPanel(new GridLayout(20, 1, 4, 4));
        // 给jpmsr2初始化20个陌生人
        JLabel[] jbls2 = new JLabel[20];
        for (int i = 0; i < 20; i++) {
            jbls2[i] = new JLabel(i+1+"", new ImageIcon("./image/mm.jpg"), JLabel.LEFT);
            jpmsr2.add(jbls2[i]);
        }

        jpmsr3 = new JPanel(new GridLayout(2, 1));
        // 将下面两个按钮加入到jpmsr3中
        jpmsr3.add(jpmsr_jb1);
        jpmsr3.add(jpmsr_jb2);

        jsp2 = new JScrollPane(jpmsr2);

        // 对 jmsry1 初始化
        jpmsr1.add(jpmsr3, "North");
        jpmsr1.add(jsp2, "Center");
        jpmsr1.add(jpmsr_jb3, "South");

        cl = new CardLayout();
        this.setLayout(cl);
        this.add(jphy1, "1");
        this.add(jpmsr1, "2");

        this.setTitle(this.ownerId);
        this.setSize(280 , 400);
        this.setIconImage((new ImageIcon("./image/qq1.jpg")).getImage());
        this.setVisible(true);

    }

    // 更新好友列表
    public void updateFriend(Message message) {
        String[] onlineFriend = message.getMessage().split(" ");
        for (String s : onlineFriend) {
            jbls[Integer.parseInt(s) - 1].setEnabled(true);     // 将在线好友的头像设置为彩色
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // 如果点击了我的好友按钮，就显示第一张卡片
        if (e.getSource() == jpmsr_jb1)
            cl.show(this.getContentPane(), "1");
        // 如果点击了陌生人按钮，就显示第二张卡片
        if (e.getSource() == jphy_jb2)
            cl.show(this.getContentPane(), "2");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // 响应用户双击事件，并得到好友编号
        if (e.getClickCount() == 2) {
            // 获取好友编号
            String friendNo = ((JLabel)e.getSource()).getText();
//            System.out.println("你希望和" + friendNo + "聊天");

            // 双击好友头像，开启聊天窗口的动作就是实例化一个窗口对象
            QqChat qqChat = new QqChat(this.ownerId, friendNo);

            // 把聊天界面加入到管理类
            QqChatManager.addQqChat(this.ownerId + " " + friendNo, qqChat);

        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        JLabel j1 = (JLabel) e.getSource();
        j1.setForeground(Color.RED);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        JLabel j1 = (JLabel) e.getSource();
        j1.setForeground(Color.BLACK);
    }

    public static void main(String[] args) {
        QqFriendList qqFriendList = new QqFriendList(new String("1633211992"));
    }
}
