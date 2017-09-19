package com.driverhelper.other.tcp.netty;

import android.util.Log;

import com.driverhelper.config.Config;
import com.driverhelper.helper.BodyHelper;
import com.driverhelper.other.Business;
import com.driverhelper.utils.ByteUtil;
import com.jaydenxiao.common.baserx.RxBus;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

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
//        TcpHelper.getInstance().disConnect();
        TcpHelper.getInstance().reConnect();
        RxBus.getInstance().post(Config.Config_RxBus.RX_NET_DISCONNECT, "");
        super.channelInactive(ctx);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        try {
            if (!ctx.channel().isActive()) {
                ctx.close();
            }
            Log.e(TAG, "channelReadComplete");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            ctx.close();
        }
//        super.channelReadComplete(ctx);
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        Log.e(TAG, "userEventTriggered");
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                //读超时
                Log.e(TAG, "===服务端=== （Reader_IDLE 读超时）");
                ctx.channel().close();
            } else if (event.state() == IdleState.WRITER_IDLE) {
                //写超时
                Log.e(TAG, "===服务端=== （Reader_IDLE 写超时）");
                ctx.channel().close();
            } else if (event.state() == IdleState.ALL_IDLE) {
                //总超时
                Log.e(TAG, "===服务端=== （ALL_IDLE 总超时）");
                ctx.channel().close();
            }
        }
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
        ctx.close();
        super.exceptionCaught(ctx, cause);
    }

}
