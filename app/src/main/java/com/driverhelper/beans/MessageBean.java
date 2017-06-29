package com.driverhelper.beans;

/**
 * Created by Administrator on 2017/6/29.
 */


/***
 * 接收的数据头
 */
public class MessageBean {

    public MessageBean() {
        headBean = new HeadBean();
    }

    public byte[] bodyBean;

    public HeadBean headBean;

    public class HeadBean {
        public String version = "80";           //协议版本号0x80
        public String messageId;            //消息id
        public String messageAttribute;         //消息属性
        public String benAttribute;             // * 二进制的消息属性
        public String phoneNumber;          //电话号码
        public String waterCode;            // * 流水号
        public int isPart;              // * 是否分包
        public int bodyLength;              // * 消息体长度
    }

}
