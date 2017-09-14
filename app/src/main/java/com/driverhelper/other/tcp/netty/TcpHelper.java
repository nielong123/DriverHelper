package com.driverhelper.other.tcp.netty;

import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.driverhelper.app.MyApplication;
import com.driverhelper.beans.db.StudyInfo;
import com.driverhelper.config.Config;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.helper.BodyHelper;
import com.driverhelper.helper.DbHelper;
import com.driverhelper.other.Business;
import com.driverhelper.other.tcp.TcpBody;
import com.driverhelper.other.tcp.TcpManager;
import com.driverhelper.other.timeTask.ClearTimerTask;
import com.driverhelper.other.timeTask.LocationInfoTimeTask;
import com.driverhelper.ui.activity.MainActivity;
import com.driverhelper.utils.ByteUtil;
import com.jaydenxiao.common.baserx.RxBus;
import com.jaydenxiao.common.commonutils.TimeUtil;
import com.jaydenxiao.common.commonutils.ToastUitl;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import io.netty.handler.codec.bytes.ByteArrayEncoder;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;

import static com.driverhelper.config.ConstantInfo.StudentInfo.studentId;
import static com.driverhelper.config.ConstantInfo.classId;
import static com.driverhelper.config.ConstantInfo.clearTimer;
import static com.driverhelper.config.ConstantInfo.clearTimerDelay;
import static com.driverhelper.config.ConstantInfo.coachId;
import static com.driverhelper.config.ConstantInfo.locationTimer;
import static com.driverhelper.config.ConstantInfo.locationTimerDelay;
import static com.driverhelper.other.tcp.TcpBody.MessageID.id0203;
import static com.driverhelper.other.tcp.netty.TcpHelper.ConnectState.CONNECTED;
import static com.driverhelper.other.tcp.netty.TcpHelper.ConnectState.CONNECTING;
import static com.driverhelper.other.tcp.netty.TcpHelper.ConnectState.DISCONNECTION;


/**
 * Created by Administrator on 2017/9/5.
 */

public class TcpHelper implements ChannelFutureListener, OnServerConnectListener {

    private final String TAG = "TcpHelper";

    public enum ConnectState {
        DISCONNECTION,
        CONNECTING,
        CONNECTED
    }

    private static TcpHelper tcpHelper;

    private static ExecutorService newFixedThreadPool;

    private static byte[] heartData;
    private static long heartDelay = 10000L;

    private ConnectState connectState = DISCONNECTION;
    private Bootstrap bootstrap;
    private EventLoopGroup group = null;
    private ChannelFuture channelFuture = null;
    private Channel channel;
    private InetSocketAddress serverAddress;

    public static TcpHelper getInstance() {
        if (tcpHelper == null) {
            synchronized (TcpHelper.class) {
                if (tcpHelper == null) {
                    tcpHelper = new TcpHelper();
                }
            }
        }
        return tcpHelper;
    }

    protected TcpHelper() {
        newFixedThreadPool = Executors.newFixedThreadPool(5);
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        if (future.isSuccess()) {
            channel = future.channel();
            onConnectSuccess();
            Log.e(TAG, "operationComplete: connected!");
        } else {
            onConnectFailed();
            Log.e(TAG, "operationComplete: connect failed!");
        }
    }

    @Override
    public void onConnectSuccess() {
        setConnectState(CONNECTED);
        if (tcpHelper.getHeartData() != null) {
            tcpHelper.startHeart();
        }
        RxBus.getInstance().post(Config.Config_RxBus.RX_NET_CONNECTED, "");
        Business.startUpDataLocationInfo();                          //开始上传定位信息
        tcpHelper.startClearTimer();
        if (!Business.is0102_OK()) {
            RxBus.getInstance().post(Config.Config_RxBus.RX_TTS_SPEAK, "终端未鉴权，请注册");
            return;
        }
//        TcpHelper.getInstance().sendAuthentication();           //鉴权
    }

    @Override
    public void onConnectFailed() {
        setConnectState(DISCONNECTION);
        RxBus.getInstance().post(Config.Config_RxBus.RX_TTS_SPEAK, "tcp链接失败");
    }

    public void connect(final InetSocketAddress socketAddress) {
        this.serverAddress = socketAddress;
        connect();
    }

    public void connect() {

        if (channel != null && channel.isActive()) {
            return;
        }
        setConnectState(CONNECTING);
        if (bootstrap == null) {
            group = new NioEventLoopGroup();
            bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            ByteBuf buf = Unpooled.copiedBuffer(new byte[]{(byte) 0x7E});
                            pipeline.addLast(new DelimiterBasedFrameDecoder(1024, buf));
                            //过滤编码
                            pipeline.addLast("decoder", new ByteArrayDecoder());
                            //过滤编码
                            pipeline.addLast("encoder", new ByteArrayEncoder());
                            pipeline.addLast("handler", new NettyClientHandler());

                        }
                    })
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
        }

        channelFuture = bootstrap.connect(serverAddress);
        channelFuture.addListener(this);
    }

    public void disConnect() {
        setConnectState(DISCONNECTION);
        if (channelFuture != null) {
            channelFuture.channel().closeFuture();
            channelFuture.channel().close();
            channelFuture = null;
        }
        if (group != null) {
            group.shutdownGracefully();
            group = null;
            tcpHelper = null;
            bootstrap = null;
        }
    }

    /****
     * 设置自动重连
     * @param disconnectionByUser
     */
    public void setAutoReConnect(boolean disconnectionByUser) {
    }

    /**
     * 发送消息的线程
     */
    private void sendData(final byte[] data) {

        if (getConnectState() == CONNECTED) {
            newFixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        channel.writeAndFlush(Unpooled.buffer().writeBytes(data)).sync();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            Log.e(TAG, "网络未连接");
        }
    }

    Runnable heartRunnable = new Runnable() {
        @Override
        public void run() {
            if (getConnectState() == CONNECTED) {
                try {
                    channelFuture.channel().writeAndFlush(Unpooled.buffer().writeBytes(heartData)).sync();
                    Thread.sleep(heartDelay * 1000);
                    newFixedThreadPool.execute(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };

    public void setHeart(byte[] heart, long delay) {
        heartData = heart;
        this.heartDelay = delay;
    }

    public void setHeartDelay(long heartDelay) {
        this.heartDelay = heartDelay;
    }

    public byte[] getHeartData() {
        return heartData;
    }

    public void setHeartData(byte[] heartData) {
        this.heartData = heartData;
    }

    protected void startHeart() {
        newFixedThreadPool.execute(heartRunnable);
    }

    public ConnectState getConnectState() {
        return connectState;
    }

    public void setConnectState(ConnectState state) {
        this.connectState = state;
    }


    /**********************************************    业务相关   **************************************************/


    protected void startClearTimer() {
        clearTimer = new Timer(true);
        clearTimer.schedule(new ClearTimerTask(), 0, clearTimerDelay);
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
        if (ConstantInfo.gpsModel.isLocation) {
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
        DbHelper.getInstance().addStudyInfo(waterCode, studentId, coachId, classId + "",
                null, time666, null, vehiclSspeed,
                distance, vehiclSspeed, timeSYS, false,
                speedGPS, direction, lat, lon, timeGPS);
        TcpManager.getInstance().put(waterCode, ByteUtil.bcdByte2bcdString(id0203));
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
                (int) (ConstantInfo.gpsModel.lon * Math.pow(10, 6)),
                (int) (ConstantInfo.gpsModel.lat * Math.pow(10, 6)),
                10,
                (int) ConstantInfo.gpsModel.speedGPS,
                (int) ConstantInfo.gpsModel.direction,
                TimeUtil.formatData(TimeUtil.dateFormatYMDHMS_, time)));
    }


    public void sendTest() {
        sendData("wriufwhelirfdjwloejrfdwlrjfdwlhfcfskideywnfr3o2".getBytes());
    }

}
