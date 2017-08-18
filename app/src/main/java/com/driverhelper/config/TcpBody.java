package com.driverhelper.config;

/**
 * Created by Administrator on 2017/6/5.
 */

public final class TcpBody {

    public static final byte[] HEAD = {(byte) 0x7E}; // 协议头
    public static final byte[] VERSION_CODE = {(byte) 0x80}; // 协议版本号
    public static final byte[] END = {(byte) 0x7E}; // 协议尾部

    public static class MessageID {
        public static byte[] register = {(byte) 0x01, (byte) 0x00}; // 注册
        public static byte[] unRegister = {(byte) 0x00, (byte) 0x03}; // 注销
        public static byte[] authentication = {(byte) 0x01, (byte) 0x02}; // 终端鉴权
        public static byte[] id0104 = {(byte) 0x01, (byte) 0x04};
        public static byte[] heart = {(byte) 0x00, (byte) 0x02};        //心跳包
        public static byte[] id0001 = {(byte) 0x00, (byte) 0x01};         //客户端通用应答
        public static byte[] locationInfoUpdata = {(byte) 0x02, (byte) 0x00};       //B.3.2.3.16　位置信息汇报
        public static byte[] findLocatInfoRequest = {(byte) 0x02, (byte) 0x01};     //查询位置信息应答
        public static byte[] updataCoachLogin = {(byte) 0x01, (byte) 0x01};             //上传教练员登录
        public static byte[] updataCoachLogout = {(byte) 0x01, (byte) 0x02};               //上传教练员登出
        public static byte[] updataStudentLogin = {(byte) 0x02, (byte) 0x01};               //上传学员登录
        public static byte[] updataStudentLogiout = {(byte) 0x02, (byte) 0x02};               //上传学员登出
        public static byte[] id0203 = {(byte) 0x02, (byte) 0x03};               //上报学时记录
        public static byte[] id0205 = {(byte) 0x02, (byte) 0x05};               //命令上报学时记录


        public static byte[] id0301 = {(byte) 0x03, (byte) 0x01};               //立即拍照
        public static byte[] id0302 = {(byte) 0x03, (byte) 0x02};               //B.4.2.3.4　查询照片应答
        public static byte[] id0303 = {(byte) 0x03, (byte) 0x03};               //B.4.2.3.4　查询照片应答
        public static byte[] id0304 = {(byte) 0x03, (byte) 0x04};               //B.4.2.3.8　上传指定照片应答
        public static byte[] id0305 = {(byte) 0x03, (byte) 0x05};               //B.4.2.3.9　照片上传初始化
        public static byte[] id0306 = {(byte) 0x03, (byte) 0x06};               //B.4.2.3.8　上传指定照片应答

        public static byte[] id0501 = {(byte) 0x05, (byte) 0x01};               //A.1.1.1.1　设置计时终端应用参数应答
        public static byte[] id0503 = {(byte) 0x05, (byte) 0x03};               //A.1.1.1.1　查询计时终端应用参数应答'

        public static byte[] id0401 = {(byte) 0x04, (byte) 0x01};               //A.1.1.1.1　请求身份认证信息
        public static byte[] id0402 = {(byte) 0x04, (byte) 0x02};               //请求统一编号信息
        public static byte[] id0403 = {(byte) 0x04, (byte) 0x03};               //A.1.1.1.1　上报车辆绑定信息


        public static byte[] id0502 = {(byte) 0x05, (byte) 0x02};               //A.1.1.1.1　设置禁训状态应答

        public static byte[] transparentInfo = {(byte) 0x09, (byte) 0x00};          //透传消息
    }

    public static final byte driving = (byte) 0x13;              //驾培业务


}
