package com.driverhelper.other.tcp.netty;

import android.text.TextUtils;
import android.util.Log;

import com.driverhelper.config.Config;
import com.driverhelper.config.ConstantInfo;
import com.driverhelper.helper.BodyHelper;
import com.driverhelper.utils.ByteUtil;
import com.jaydenxiao.common.baserx.RxBus;
import com.orhanobut.logger.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import static com.driverhelper.config.ConstantInfo.locationTimer;

/**
 * Created by Administrator on 2017/9/5.
 */

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private static TcpHelper tcpHelper = null;

    public NettyClientHandler(TcpHelper tcpHelper) {
        super();
        this.tcpHelper = tcpHelper;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Log.e("ClientHandler", "-------重连回调------");
        tcpHelper.setConnectState(TcpHelper.ConnectState.DISCONNECTION);
        RxBus.getInstance().post(Config.Config_RxBus.RX_NET_DISCONNECT, "tcp连接已断开");
        if(locationTimer != null){
            locationTimer.cancel();
        }
        if (!tcpHelper.disConnectByUser) {
            tcpHelper.connect();
        }
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Log.e("NettyClientHandl", "网络异常!");
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Log.e("NettyClientHandl", "registered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.e("NettyClientHandler", "=====     连接成功回调    =====");
        tcpHelper.setConnectState(TcpHelper.ConnectState.CONNECTED);
        if (tcpHelper.getHeartData() != null) {
            tcpHelper.startHeart();
        }
        RxBus.getInstance().post(Config.Config_RxBus.RX_TTS_SPEAK, "tcp连接成功");
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
        tcpHelper.startUpDataLocationInfo();                          //开始上传定位信息
        tcpHelper.startClearTimer();
        super.channelActive(ctx);
    }

    /****
     * 读
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf) msg;
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        Log.e("recever:", new String(data));
        if (data.length != 0) {
            BodyHelper.handleReceiveInfo(ByteUtil.rebackData(data));
        }
        super.channelRead(ctx, msg);
    }

}
