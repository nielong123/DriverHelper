package com.driverhelper.beans;

/**
 * Created by Administrator on 2017/6/29.
 */


/***
 * 接收的数据
 */
public class MessageBean {

    public MessageBean() {
        headBean = new HeadBean();
    }

    public byte[] bodyBean;             //数据体

    public HeadBean headBean;               //数据头

    public class HeadBean {

        public String version = "80";           //协议版本号0x80
        public String messageId;            //消息id
        public String messageAttribute;         //消息属性
        public String benAttribute;             // * 二进制的消息属性
        public String phoneNumber;          //电话号码
        public String waterCode;            // * 流水号
        public int isPart;              // * 是否分包
        public EncapsulationInfo encapsulationInfo = new EncapsulationInfo();     //有分包的时候才有这个
        public int bodyLength;              // * 消息体长度

        public class EncapsulationInfo {
            public int totle;                   //分包项的总数
            public int index;                   //分包项的计数
        }
    }

}
