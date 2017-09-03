package com.driverhelper.other.tcp;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Toast;

import com.driverhelper.app.MyApplication;
import com.driverhelper.beans.db.StudyInfo;
import com.driverhelper.config.Config;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.helper.BodyHelper;
import com.driverhelper.helper.DbHelper;
import com.driverhelper.other.timeTask.ClearTimerTask;
import com.driverhelper.other.timeTask.LocationInfoTimeTask;
import com.driverhelper.utils.ByteUtil;
import com.jaydenxiao.common.baserx.RxBus;
import com.jaydenxiao.common.commonutils.TimeUtil;
import com.jaydenxiao.common.commonutils.ToastUitl;
import com.orhanobut.logger.Logger;
import com.vilyever.socketclient.SocketClient;
import com.vilyever.socketclient.helper.SocketClientDelegate;
import com.vilyever.socketclient.helper.SocketClientReceivingDelegate;
import com.vilyever.socketclient.helper.SocketPacketHelper;
import com.vilyever.socketclient.helper.SocketResponsePacket;
import com.vilyever.socketclient.util.CharsetUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import static com.driverhelper.config.ConstantInfo.StudentInfo.studentId;
import static com.driverhelper.config.ConstantInfo.classId;
import static com.driverhelper.config.ConstantInfo.clearTimer;
import static com.driverhelper.config.ConstantInfo.clearTimerDelay;
import static com.driverhelper.config.ConstantInfo.coachId;
import static com.driverhelper.config.ConstantInfo.isDisConnectByUser;
import static com.driverhelper.config.ConstantInfo.locationTimer;
import static com.driverhelper.config.ConstantInfo.locationTimerDelay;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0203;
import static com.vilyever.socketclient.helper.SocketPacketHelper.ReadStrategy.AutoReadToTrailer;

/**
 * Created by Administrator on 2017/6/5.
 */

public class TcpHelper {

    private static volatile TcpHelper tcpHelper;
    public static boolean isConnected;
    private static SocketClient socketClient;

    static public TcpHelper getInstance() {
        if (tcpHelper == null) {
            synchronized (TcpHelper.class) {
                if (tcpHelper == null) {
                    tcpHelper = new TcpHelper();
                }
                if (socketClient == null) {
                    socketClient = new SocketClient();
                }
            }
        }
        return tcpHelper;
    }


    /***
     *
     * @param ip
     * @param port
     * @param timeOut
     */
    public void connect(String ip, String port, int timeOut) {
        if (!TextUtils.isEmpty(ip) && !TextUtils.isEmpty(port)) {
            tcpHelper.getLocalSocketClient(ip, port, timeOut).connect();
        } else {
            ToastUitl.show("请设置正确的端口号和IP地址", Toast.LENGTH_SHORT);
        }
    }

    public SocketClient getLocalSocketClient(String ip, String port, int timeOut) {
        if (socketClient == null) {
            socketClient = new SocketClient();
        }
        __i__setupAddress(socketClient, ip, port, timeOut);
        __i__setupEncoding(socketClient);
        __i__setupConstantHeartBeat(socketClient);
        __i__setupVariableHeartBeat(socketClient);
        __i__setConnectStateCallBack(socketClient);
        __i__setReceiverCallBack(socketClient);
        return socketClient;
    }

    /* Private Methods */

    /**
     * 设置远程端地址信息
     */
    private void __i__setupAddress(SocketClient socketClient, String ip, String port, int timeOut) {
        socketClient.getAddress().setRemoteIP(ip); // 远程端IP地址
        socketClient.getAddress().setRemotePort(port); // 远程端端口号
        socketClient.getAddress().setConnectionTimeout(timeOut); // 连接超时时长，单位毫秒
    }

    /**
     * 设置自动转换String类型到byte[]类型的编码
     * 如未设置（默认为null），将不能使用{@link SocketClient#sendString(String)}发送消息
     * 如设置为非null（如UTF-8），在接受消息时会自动尝试在接收线程（非主线程）将接收的byte[]数据依照编码转换为String，在{@link SocketResponsePacket#getMessage()}读取
     */
    private void __i__setupEncoding(SocketClient socketClient) {
        socketClient.setCharsetName(CharsetUtil.UTF_8); // 设置编码为UTF-8
    }

    private void __i__setupConstantHeartBeat(SocketClient socketClient) {
        socketClient.getHeartBeatHelper().setHeartBeatInterval(10 * 1000); // 设置自动发送心跳包的间隔时长，单位毫秒
        socketClient.getHeartBeatHelper().setSendHeartBeatEnabled(true); // 设置允许自动发送心跳包，此值默认为false
    }

    private void __i__setupVariableHeartBeat(SocketClient socketClient) {
        /**
         * 设置自动发送的心跳包信息
         * 此信息动态生成
         *
         * 每次发送心跳包时自动调用
         */
//        socketClient.getHeartBeatHelper().setSendDataBuilder(new SocketHeartBeatHelper.SendDataBuilder() {
//            @Override
//            public byte[] obtainSendHeartBeatData(SocketHeartBeatHelper helper) {
//                return BodyHelper.makeHeart();              //心跳
//            }
//        });
    }

    private void __i__setReceiverCallBack(SocketClient socketClient) {
        socketClient.getSocketPacketHelper().setReadStrategy(AutoReadToTrailer);
        socketClient.getSocketPacketHelper().setReceiveTrailerData(new byte[]{(byte) 0x7E});
        socketClient.registerSocketClientReceiveDelegate(new SocketClientReceivingDelegate() {
            @Override
            public void onReceivePacketBegin(SocketClient client, SocketResponsePacket packet) {
//                Logger.d("onReceive", "SocketClient: onReceivePacketBegin: " + packet.hashCode());
            }

            /***
             * 接收数据
             * @param client
             * @param packet
             */
            @Override
            public void onReceivePacketEnd(SocketClient client, SocketResponsePacket packet) {
//                ByteUtil.printRecvHexString(packet.getData());
            }

            @Override
            public void onReceivePacketCancel(SocketClient client, SocketResponsePacket packet) {
//                Logger.d("onReceive", "SocketClient: onReceivePacketCancel: ");
            }

            @Override
            public void onReceivingPacketInProgress(SocketClient client, SocketResponsePacket packet, float progress, int receivedLength) {
//                Logger.d("onReceive", "SocketClient: onReceivingPacketInProgress: " + packet.hashCode() + " : " + progress + " : " + receivedLength);
            }
        });
    }


    private void __i__setConnectStateCallBack(final SocketClient socketClient) {
        socketClient.registerSocketClientDelegate(new SocketClientDelegate() {
            @Override
            public void onConnected(SocketClient client) {
                isConnected = true;
                isDisConnectByUser = false;
                Logger.d("onConnected", "SocketClient: onConnected");
                RxBus.getInstance().post(Config.Config_RxBus.RX_TTS_SPEAK, "tcp连接成功");
                if (client.getSocketPacketHelper().getReadStrategy() == SocketPacketHelper.ReadStrategy.Manually) {
                    client.readDataToLength(CharsetUtil.stringToData("Server accepted", CharsetUtil.UTF_8).length);
                }
                if (TextUtils.isEmpty(ByteUtil.getString(ConstantInfo.institutionNumber)) ||
                        TextUtils.isEmpty(ByteUtil.getString(ConstantInfo.platformNum)) ||
                        TextUtils.isEmpty(ByteUtil.getString(ConstantInfo.terminalNum)) ||
                        TextUtils.isEmpty(ByteUtil.getString(ConstantInfo.certificatePassword)) ||
                        TextUtils.isEmpty(ConstantInfo.terminalCertificate)) {
                    RxBus.getInstance().post(Config.Config_RxBus.RX_TTS_SPEAK, "终端未注册，请注册");
                    return;
                }
                RxBus.getInstance().post(Config.Config_RxBus.RX_NET_CONNECTED, null);
                TcpHelper.getInstance().sendAuthentication();           //鉴权
                startUpDataLocationInfo();                          //开始上传定位信息
                startClearTimer();
            }

            @Override
            public void onDisconnected(final SocketClient client) {
                isConnected = false;
                RxBus.getInstance().post(Config.Config_RxBus.RX_NET_DISCONNECT, "tcp连接已断开");
                locationTimer.cancel();
                if (!isDisConnectByUser) {
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... params) {
                            try {
                                Thread.sleep(3 * 1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            client.connect();
                            return null;
                        }

                        @Override
                        protected void onPostExecute(Void aVoid) {
                            super.onPostExecute(aVoid);
                        }
                    }.execute();
                }
            }

            @Override
            public void onResponse(final SocketClient client, @NonNull SocketResponsePacket responsePacket) {
                byte[] data = responsePacket.getData(); // 获取接收的byte数组，不为null
                if (data.length != 0) {
                    BodyHelper.handleReceiveInfo(ByteUtil.rebackData(data));
                }
            }
        });
    }


    private void startUpDataLocationInfo() {
        RxBus.getInstance().post(Config.Config_RxBus.RX_TTS_SPEAK, "开始上传位置信息");
//        locationTimer = new Timer(true);
//        locationTimer.schedule(new LocationInfoTimeTask(), 1000, locationTimerDelay);          //暂定十秒一次,要改成可以设置的
    }

    private void startClearTimer() {
        clearTimer = new Timer(true);
        clearTimer.schedule(new ClearTimerTask(), 0, clearTimerDelay);
    }


    public void disConnect() {
        if (locationTimer != null) {
            locationTimer.cancel();
        }
        if (clearTimer != null) {
            clearTimer.cancel();
        }
        isDisConnectByUser = true;
        tcpHelper.__i__disConnect(socketClient);
        socketClient = null;
        tcpHelper = null;
    }

    private void __i__disConnect(SocketClient socketClient) {
        if (!socketClient.isDisconnecting()) {
            socketClient.disconnect();
        }
    }

    private void sendData(final byte[] data) {
        if (socketClient == null || !socketClient.isConnected()) {
            RxBus.getInstance().post(Config.Config_RxBus.RX_TTS_SPEAK, "网络未连接");
            return;
        }
        socketClient.sendData(data);
    }

    public void sendCommonResponse(int id, int state) {
        sendData(BodyHelper.makeClientCommonResponse(id, state));
    }

    /***
     * 终端注册
     */
    public void sendRegistInfo() {
        sendData(BodyHelper.makeRegist());
    }

    /****
     * 终端注销
     */
    public void sendCancellation() {
        byte[] data = BodyHelper.makeUnRegist();
        byte[] waterByte = new byte[2];
        System.arraycopy(data, 14, waterByte, 0, 2);
        TcpManager.getInstance().put(ByteUtil.byte2int(waterByte), ByteUtil.bcdByte2bcdString(TcpBody.MessageID.unRegister));
        sendData(data);
    }

    /***
     * 终端鉴权
     */
    public void sendAuthentication() {
        byte[] data = BodyHelper.makeAuthentication();
        byte[] waterByte = new byte[2];
        System.arraycopy(data, 14, waterByte, 0, 2);
        TcpManager.getInstance().put(ByteUtil.byte2int(waterByte), ByteUtil.bcdByte2bcdString(TcpBody.MessageID.authentication));
//        RxBus.getInstance().post(Config.Config_RxBus.RX_TTS_SPEAK, "开始鉴权");
        sendData(data);
    }


    /****
     * 终端消息查询
     * @param id        对应查询的消息id'
     * @param idList
     */
    public void send0104(int id, List<byte[]> idList) {
        sendData(BodyHelper.make0104(id, idList));
    }

    /****
     * 终端消息查询
     * @param id        对应查询的消息id'
     */
    public void sendAll0104(int id) {
        sendData(BodyHelper.makeAll0104(id));
    }

    /***
     * 发送位置信息
     */
    public void sendMakeLocationInfo(int lon, int lat, int speedVehicle, int speedGPS, int direction, String time) {

        byte[] data;
        if (MyApplication.getInstance().isLocation) {
            data = BodyHelper.makeLocationInfo("00000000",
                    "40080000",
                    lon,
                    lat,
                    speedVehicle,
                    speedGPS,
                    direction,
                    time + "",
                    -2000, -2000, -2000, -2000);
            sendData(data);
        } else {                //定位不成功报警标识应该不同
            data = BodyHelper.makeLocationInfo("00000000",
                    "40080000",
                    lon,
                    lat,
                    speedVehicle,
                    speedGPS,
                    direction,
                    time + "",
                    -2000, -2000, -2000, -2000);
            sendData(data);
        }
        byte[] waterByte = new byte[2];
        System.arraycopy(data, 14, waterByte, 0, 2);
        TcpManager.getInstance().put(ByteUtil.byte2int(waterByte), ByteUtil.bcdByte2bcdString(TcpBody.MessageID.locationInfoUpdata));
    }

    /****
     * 发送教练登录信息
     */
    public void sendCoachLogin(String idCard, String coachnum, String carType) {
        sendData(BodyHelper.makeCoachLogin(idCard, coachnum, carType));
    }

    /*****
     * 教练员登出
     */
    public void sendCoachLogout() {
        sendData(BodyHelper.makeCoachLogout(coachId));
    }


    /******
     * 学员登录
     */
    public void sendStudentLogin(String coachNum, String studentNum) {
        sendData(BodyHelper.makeStudentLogin(coachNum, studentNum));
    }

    /******
     * 学员登出
     */
    public void sendStudentLogiout() {
        sendData(BodyHelper.makeStudentLogiout(studentId));
    }

    /*****
     * 发送学时信息
     */
    public void sendStudyInfo(String time666, int studyCode, byte updataType, String studentId, String coachId,
                              int vehiclSspeed, int distance, int lon, int lat, int speedGPS,
                              int direction, long timeSYS, long timeGPS, byte recordType) {
        byte[] data = BodyHelper.makeSendStudyInfo(time666, studyCode, updataType, studentId, coachId, vehiclSspeed, distance, lon, lat, speedGPS, direction, timeSYS,
                recordType);
        sendData(data);
        ByteUtil.printHexString("学时记录    =   ", data);
        byte[] waterByte = new byte[2];
        System.arraycopy(data, 14, waterByte, 0, 2);
        int waterCode = ByteUtil.byte2int(waterByte);
        TcpManager.getInstance().put(waterCode, ByteUtil.bcdByte2bcdString(id0203));
        DbHelper.getInstance().addStudyInfo(waterCode, studentId, coachId, classId + "",
                null, time666, null, vehiclSspeed,
                distance, vehiclSspeed, timeSYS, false,
                speedGPS, direction, lat, lon, timeGPS);
    }

    /*****
     * 发送学时信息
     */
    public void reSendRStudyInfo(List<StudyInfo> list) {
        ToastUitl.show("查询到未上传记录，共" + list.size() + "条，开始上传", Toast.LENGTH_SHORT);
        for (StudyInfo data : list) {
            sendData(BodyHelper.makeReSendStudyInfo(data));
            String fileName = data.getPhotoPath();
            if (TextUtils.isEmpty(fileName)) {
//                TcpHelper.getInstance().send0305(fileName.replace(".png", ""), data.getCoachId(), (byte) 0x01, (byte) 0x01, (byte) 0x01, (byte) 0x01, 1, bytes.length);
//                TcpHelper.getInstance().send0306(fileName.replace(".png", ""), bytes);
            }
        }
    }

    public void send0205(byte type) {
        sendData(BodyHelper.make0205(type));
    }

    public void send0203(StudyInfo info) {
//        if (ConstantInfo.StudentInfo.studentId != null) {
        sendData(BodyHelper.make0203((byte) 0x01, (byte) 0x01, info));
//        } else {
//            RxBus.getInstance().post(Config.Config_RxBus.RX_TTS_SPEAK, "学员未签到");
//        }
    }

    public void send0301(byte updataType) {
        sendData(BodyHelper.make0301((byte) 0x01, updataType, (byte) 0x04, (byte) 0x02));
    }

    public void send0304(byte eventType) {
        sendData(BodyHelper.make0304(eventType));
    }

    /****
     *
     * @param photoId       照片id
     * @param id            学员或者教练id
     * @param updataType        上传类型
     * @param carmerId          相机编号
     * @param eventType                 发起图片的事件类型
     * @param totle         总包数
     * @param photoSize         照片总大小
     */
    public void send0305(String photoId, String id, byte updataType, byte carmerId, byte photoSizeXxX, byte eventType, int totle, int photoSize) {
        sendData(BodyHelper.make0305(photoId, id, updataType, carmerId, photoSizeXxX, eventType, totle, photoSize));
    }

    /*****
     *
     * @param photoId  照片id
     * @param photoData     总的照片数据
     */
    public void send0306(String photoId, byte[] photoData) {

        List<byte[]> list = BodyHelper.make0306Part(photoId, photoData);
        if (list.size() > 1) {
            List<byte[]> res = new ArrayList<>();
            for (byte[] data : list) {
                int index = list.indexOf(data) + 1;
                res.add(BodyHelper.make0306(data, list.size(), index, true));
            }
            for (byte[] data : res) {
                sendData(data);
            }
        }
    }

    public void send0302() {
        sendData(BodyHelper.make0302((byte) 0x01));
    }

    public void send0303(List<String> list) {
        final int pageSize = 10;
//        list = null;
        if (list != null) {
            if (list.size() > pageSize) {
                /*******************************这段代码可能有问题**********************/
                int index = (list.size() / pageSize) + 1;
                for (int i = 0; i < index; i++) {
                    if (i != index - 1) {
                        List<String> list1 = new ArrayList<>();
                        for (int j = 0; j < pageSize * i; j++) {
                            list1.add(list.get(j));
                        }
                        sendData(BodyHelper.make0303((byte) 0x00, list.size(), list1));
                    } else {
                        List<String> list1 = new ArrayList<>();
                        for (int j = 0; j < list.size() % pageSize; j++) {
                            list1.add(list.get(j));
                        }
                        sendData(BodyHelper.make0303((byte) 0x01, list.size(), list1));
                    }
                }
                /***************************************************************************/
                return;
            } else {
                sendData(BodyHelper.make0303((byte) 0x01, list.size(), list));
            }

        } else {
            sendData(BodyHelper.make0303((byte) 0x01, 0, new ArrayList<String>()));
        }

    }

    public void send0503(int parameter1,
                         int parameter2,
                         int parameter3,
                         int parameter4,
                         int parameter5,
                         int parameter6,
                         int parameter7,
                         int parameter8,
                         int parameter9,
                         int parameter10,
                         int parameter11) {
        sendData(BodyHelper.make0503(parameter1,
                parameter2,
                parameter3,
                parameter4,
                parameter5,
                parameter6,
                parameter7,
                parameter8,
                parameter9,
                parameter10,
                parameter11));
    }

    public void send0401() {
        sendData(BodyHelper.make0401((byte) 0x01, (byte) 0x01));
    }

    public void send0402(byte type, String id) {
        sendData(BodyHelper.make0402(type, id));
    }

    public void send0403() {
        sendData(BodyHelper.make0403(""));
    }

    public void send0502(byte result, byte state, byte length, String str) {
        sendData(BodyHelper.make0502(result, state, length, str));
    }

    public void sendLocationInfo() {
        long time = TimeUtil.getTime() / 1000;
        sendData(BodyHelper.makeFindLocatInfoRequest("00000000",
                "40080000",
                (int) (MyApplication.getInstance().lon * Math.pow(10, 6)),
                (int) (MyApplication.getInstance().lat * Math.pow(10, 6)),
                10,
                (int) MyApplication.getInstance().speedGPS,
                (int) MyApplication.getInstance().direction,
                TimeUtil.formatData(TimeUtil.dateFormatYMDHMS_, time)));
    }


}
