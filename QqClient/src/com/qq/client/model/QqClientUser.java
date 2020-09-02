package com.qq.client.model;

import com.qq.common.User;

/**
 *  用户身份验证
 */

public class QqClientUser {

    public boolean checkUser(User user) {
        QqClientConnectServer clientConnectServer = new QqClientConnectServer();
        return clientConnectServer.sendLoginInfoToServer(user);
    }

}
