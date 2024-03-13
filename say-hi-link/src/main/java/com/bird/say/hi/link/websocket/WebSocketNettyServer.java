package com.bird.say.hi.link.websocket;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.bird.say.hi.link.handler.HeartBeatHandler;
import com.bird.say.hi.link.handler.WebSocketHandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangruilong <zhangruilong03@kuaishou.com>
 * Created on 2023-12-29
 * http://www.websocket-test.com/
 */
@Component
@Slf4j
public class WebSocketNettyServer {
    @Value("${server.port}")
    private int port;

    /**
     * webSocket协议名
     */
    private static final String WEBSOCKET_PROTOCOL = "WebSocket";


    /**
     * webSocket路径
     */
    @Value("${webSocket.netty.path:/webSocket}")
    private String webSocketPath;

    // @PostConstruct
    // public void start() throws Exception {
    //     EventLoopGroup bossGroup = new NioEventLoopGroup();
    //     EventLoopGroup workGroup = new NioEventLoopGroup();
    //     ServerBootstrap bootstrap = new ServerBootstrap();
    //     // bossGroup辅助客户端的tcp连接请求, workGroup负责与客户端之前的读写操作
    //     bootstrap.group(bossGroup,workGroup);
    //     // 设置NIO类型的channel
    //     bootstrap.channel(NioServerSocketChannel.class);
    //     // 设置监听端口
    //     bootstrap.localAddress(new InetSocketAddress(10008));
    //     // 连接到达时会创建一个通道
    //     bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
    //         @Override
    //         protected void initChannel(SocketChannel ch) throws Exception {
    //             // 流水线管理通道中的处理程序（Handler），用来处理业务
    //             // webSocket协议本身是基于http协议的，所以这边也要使用http编解码器
    //             ch.pipeline().addLast(new HttpServerCodec());
    //             ch.pipeline().addLast(new ObjectEncoder());
    //             // 以块的方式来写的处理器
    //             ch.pipeline().addLast(new ChunkedWriteHandler());
    //             /*
    //             说明：
    //             1、http数据在传输过程中是分段的，HttpObjectAggregator可以将多个段聚合
    //             2、这就是为什么，当浏览器发送大量数据时，就会发送多次http请求
    //              */
    //             ch.pipeline().addLast(new HttpObjectAggregator(8192));
    //             /*
    //             说明：
    //             1、对应webSocket，它的数据是以帧（frame）的形式传递
    //             2、浏览器请求时 ws://localhost:58080/xxx 表示请求的uri
    //             3、核心功能是将http协议升级为ws协议，保持长连接
    //             */
    //             ch.pipeline().addLast(new WebSocketServerProtocolHandler(webSocketPath, WEBSOCKET_PROTOCOL, true,
    //             65536 * 10));
    //             // 自定义的handler，处理业务逻辑
    //             ch.pipeline().addLast(new WebSocketHandler());
    //
    //         }
    //     });
    //
    //     ChannelFuture channelFuture = bootstrap.bind().sync();
    //     log.info("Server started and listen on:{}",channelFuture.channel().localAddress());

    // }

    @PostConstruct
    public void start() throws Exception {
        // 主线程组
        EventLoopGroup group = new NioEventLoopGroup();
        // 创建从线程组，处理主线程组分配下来的io操作
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap sb = new ServerBootstrap();
            // 存放已完成三次握手的请求的等待队列
            sb.option(ChannelOption.SO_BACKLOG, 1024);
            //  要求高实时性，有数据时马上发送，就将该选项设置为true关闭Nagle算法；
            //  如果要减少发送次数，就设置为false，会累积一定大小后再发送
            sb.option(ChannelOption.TCP_NODELAY, true);
            sb.group(group, bossGroup) // 绑定线程池
                    .channel(NioServerSocketChannel.class) // 指定使用的channel
                    .localAddress(this.port)// 绑定监听端口
                    .childHandler(new ChannelInitializer<SocketChannel>() { // 绑定客户端连接时候触发操作
                        @Override
                        protected void initChannel(SocketChannel ch) {
                            log.info("收到新连接");
                            // websocket协议本身是基于http协议的，所以这边也要使用http解编码器
                            ch.pipeline().addLast(new HttpServerCodec());
                            // 以块的方式来写的处理器
                            ch.pipeline().addLast(new ChunkedWriteHandler());
                            // http请求数据分段聚合
                            ch.pipeline().addLast(new HttpObjectAggregator(8192));
                            // 对客户端，如果在40秒内没有向服务端发送心跳，就主动断开
                            ch.pipeline().addLast(new IdleStateHandler(40, 0, 0));
                            ch.pipeline().addLast(new HeartBeatHandler());
                            ch.pipeline().addLast(new WebSocketHandler());
                            ch.pipeline().addLast(new WebSocketServerProtocolHandler(
                                    webSocketPath, WEBSOCKET_PROTOCOL, true, 65536 * 10
                            ));
                        }
                    });
            // 配置完成，开始绑定server，通过调用sync同步方法阻塞直到绑定成功
            ChannelFuture channelFuture = sb.bind().sync();
            log.info("Server started and listen on:{}",channelFuture.channel().localAddress());
            // 对关闭通道进行监听
            channelFuture.channel().closeFuture().sync();
        } finally {
            // 释放线程池资源
            group.shutdownGracefully().sync();
            bossGroup.shutdownGracefully().sync();
        }
    }
}
