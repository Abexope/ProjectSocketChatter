package com.qq.server.view;

import com.qq.server.model.QqServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  服务器控制台界面
 *      - 启动服务器
 *      - 关闭服务器
 *      - 管理和监控用户
 */

public class QqServerFrame extends JFrame implements ActionListener {

    JPanel jp1;
    JButton jb1, jb2;

    public QqServerFrame() {
        jp1 = new JPanel();
        jb1 = new JButton("启动服务器");
        jb1.addActionListener(this);
        jb2 = new JButton("关闭服务器");
        jp1.add(jb1);
        jp1.add(jb2);

        this.add(jp1);
        this.setSize(300, 100);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIconImage((new ImageIcon("./image/server.jpg")).getImage());
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jb1) {
            new QqServer();
        }
    }

    public static void main(String[] args) {
        QqServerFrame qqServerFrame = new QqServerFrame();
    }
}
