package com.driverhelper.other.tcp.netty;

import android.util.Log;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by Administrator on 2017/9/5.
 */

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private NettyClient nettyClient = null;

    public NettyClientHandler(NettyClient nettyClient) {
        super();
        this.nettyClient = nettyClient;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Log.d("ClientHandler", "-------重连回调------");
        nettyClient.setConnectState(NettyClient.ConnectState.DISCONNECTION);
        nettyClient.connect();
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Log.d("NettyClientHandl", "网络异常!");
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Log.d("NettyClientHandl", "registered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.d("NettyClientHandler", "=====连接成功回调=====");
        nettyClient.setConnectState(NettyClient.ConnectState.CONNECTED);
        if (nettyClient.getHeartData() != null) {
            nettyClient.startHeart("hello world".getBytes(), 1000L);
        }
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
        byte[] result1 = new byte[buf.readableBytes()];
        buf.readBytes(result1);
        Log.d("recever:", new String(result1));
        super.channelRead(ctx, msg);
    }

}
