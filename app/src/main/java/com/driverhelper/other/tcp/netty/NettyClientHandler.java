package com.driverhelper.other.tcp.netty;

import android.util.Log;

import com.driverhelper.helper.BodyHelper;
import com.driverhelper.utils.ByteUtil;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

import static android.R.attr.data;

/**
 * Created by Administrator on 2017/9/5.
 */

@ChannelHandler.Sharable
public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    final String TAG = "NettyClientHandler";

    public NettyClientHandler() {
        super();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        Log.e(TAG, "channelRegistered");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        Log.e(TAG, "channelUnregistered");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Log.e(TAG, "channelActive");
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Log.e(TAG, "channelInactive");
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        Log.e(TAG, "channelReadComplete");
        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        Log.e(TAG, "userEventTriggered");
        super.userEventTriggered(ctx, evt);
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        Log.e(TAG, "channelWritabilityChanged");
        super.channelWritabilityChanged(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        byte[] data = (byte[]) msg;
        Log.d("recever:", new String(data));
        ByteUtil.printHexString("接收到数据 : ", data);
        if (data.length != 0) {
            BodyHelper.handleReceiveInfo(ByteUtil.rebackData(data));
        }
        super.channelRead(channelHandlerContext, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Log.d("NettyClientHandl", "网络异常!");
        super.exceptionCaught(ctx, cause);
        ctx.close();
    }

}
