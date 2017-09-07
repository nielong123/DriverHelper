package com.driverhelper.other.tcp.netty;

import android.util.Log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import static com.driverhelper.other.tcp.netty.NettyClient.ConnectState.CONNECTED;
import static com.driverhelper.other.tcp.netty.NettyClient.ConnectState.CONNECTING;
import static com.driverhelper.other.tcp.netty.NettyClient.ConnectState.DISCONNECTION;


/**
 * Created by Administrator on 2017/9/5.
 */

public class NettyClient {

    private final String TAG = "NettyClient";

    private static NettyClient nettyClient;

    static ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(5);

    private String ip;
    private int port;
    private byte[] heartData;
    private long heartDelay = 10000l;

    public enum ConnectState {
        DISCONNECTION,
        CONNECTING,
        CONNECTED
    }

    private boolean disconnectionByUser;

    private ConnectState connectState = DISCONNECTION;

    private EventLoopGroup group = null;
    private Bootstrap bootstrap = null;
    private ChannelFuture channelFuture = null;

    public static NettyClient getInstance() {
        if (nettyClient == null) {
            synchronized (NettyClient.class) {
                if (nettyClient == null) {
                    nettyClient = new NettyClient();
                }
            }
        }
        return nettyClient;
    }

    private NettyClient() {
        init();
    }

    private void init() {
        setConnectState(DISCONNECTION);
        bootstrap = new Bootstrap();
        group = new NioEventLoopGroup();
        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                ChannelPipeline pipeline = socketChannel.pipeline();
                pipeline.addLast("clientHandler", new NettyClientHandler(nettyClient));
            }
        });
    }


    public void connect(String ip, int port) {
        this.ip = ip;
        this.port = port;
        connect();
    }

    public void connect() {
        if (getConnectState() != CONNECTED) {
            setConnectState(CONNECTING);
            channelFuture = bootstrap.connect(ip, port);
            channelFuture.addListener(listener);
            disconnectionByUser = false;
        }
    }

    public void disConnection() {
        setConnectState(DISCONNECTION);
        disconnectionByUser = true;
        if (channelFuture != null) {
            channelFuture.channel().closeFuture();
            channelFuture.channel().close();
            channelFuture = null;
        }
        if (group != null) {
            group.shutdownGracefully();
            group = null;
            nettyClient = null;
            bootstrap = null;
        }
    }


    private ChannelFutureListener listener = new ChannelFutureListener() {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if (future.isSuccess()) {
                channelFuture = future;
                setConnectState(CONNECTED);
            } else {
                setConnectState(DISCONNECTION);
                if (!disconnectionByUser) {
                    future.channel().eventLoop().schedule(new Runnable() {
                        @Override
                        public void run() {
                            connect();
                        }
                    }, 3L, TimeUnit.SECONDS);
                }
            }
        }
    };

    /**
     * 发送消息的线程
     */
    public void sendData(final byte[] data) {

        if(getConnectState() == CONNECTED){
            newFixedThreadPool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        channelFuture.channel().writeAndFlush(Unpooled.buffer().writeBytes(data)).sync();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }else{
            Log.e(TAG,"网络未连接");
        }
    }


    public void startHeart(byte[] heart, long delay) {
        heartData = heart;
        this.heartDelay = delay;
        newFixedThreadPool.execute(heartRunnable);
    }

    public void setHeartDelay(long heartDelay){
        this.heartDelay = heartDelay;
    }

    Runnable heartRunnable = new Runnable() {
        @Override
        public void run() {
            if (getConnectState() == CONNECTED) {
                try {
                    channelFuture.channel().writeAndFlush(Unpooled.buffer().writeBytes(heartData)).sync();
                    Thread.sleep(heartDelay);
                    newFixedThreadPool.execute(this);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    };


    public byte[] getHeartData() {
        return heartData;
    }

    public void setHeartData(byte[] heartData) {
        this.heartData = heartData;
    }

    public ConnectState getConnectState() {
        return connectState;
    }

    public void setConnectState(ConnectState state) {
        this.connectState = state;
    }


}
