package com.qq.common;

/**
 *  定义包的种类接口
 */

public interface MessageType {

    String message_succeed = "1";      // 登陆成功
    String message_login_fail = "2";    // 登陆失败
    String message_comm_mes = "3";      // 普通信息包
    String message_get_onlineFriend = "4";  // 要求获取在线好友
    String message_ret_onlineFriend = "5";  // 返回在线好友的包
}
