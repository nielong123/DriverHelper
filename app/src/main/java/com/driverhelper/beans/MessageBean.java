package com.driverhelper.beans;

/**
 * Created by Administrator on 2017/6/29.
 */


import com.driverhelper.utils.ByteUtil;

/***
 * 接收的数据
 */
public class MessageBean {

    public byte[] bodyBean;             //数据体
    public HeadBean headBean;               //数据头
    public ThroughExpand throughExpand;         //透传消息消息体

    public MessageBean() {
        headBean = new HeadBean();
    }

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

    /****
     * 透传消息
     */
    public class ThroughExpand {
        public byte type;           //透传消息类型
        public byte[] id = new byte[2];              //穿透消息id
        public byte[] expandAttribute = new byte[2];     //拓展属性
        public byte[] drivingPackageNumber = new byte[2];        //驾培包序号
        public byte[] terminalNum = new byte[16];                 //终端编号
        public byte[] dataLength = new byte[4];     //数据长度
        public byte[] data;            //数据内容
        public int length;
    }


    public void getThroughExpand(byte[] bodyBean) {
        this.throughExpand = new ThroughExpand();
        throughExpand.type = bodyBean[0];
        System.arraycopy(bodyBean, 1, throughExpand.id, 0, throughExpand.id.length);
        System.arraycopy(bodyBean, 3, throughExpand.expandAttribute, 0, throughExpand.expandAttribute.length);
        System.arraycopy(bodyBean, 5, throughExpand.drivingPackageNumber, 0, throughExpand.drivingPackageNumber.length);
        System.arraycopy(bodyBean, 6, throughExpand.terminalNum, 0, throughExpand.terminalNum.length);
        System.arraycopy(bodyBean, 23, throughExpand.dataLength, 0, throughExpand.dataLength.length);
        throughExpand.length = ByteUtil.byte2int(throughExpand.dataLength);
        throughExpand.data = new byte[throughExpand.length];
        System.arraycopy(bodyBean, 27, throughExpand.data, 0, throughExpand.length);
    }

}
