package com.bird.say.hi.link.handler;

import java.util.HashMap;
import java.util.Map;

import cn.hutool.core.net.url.UrlBuilder;
import cn.hutool.core.util.CharsetUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.extern.slf4j.Slf4j;

/**
 * @author zhangruilong <zhangruilong03@kuaishou.com>
 * Created on 2023-12-29
 */
@Slf4j
public class WebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    public static ChannelGroup channelGroup;

    static {
        channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().id() + "与客户端建立连接，通道开启！");
        // 添加到channelGroup通道组
        WebSocketChannelHandler.channelGroup.add(ctx.channel());
    }

    /**
     * 要想实现客户端感知服务端的存活情况，需要进行双向的心跳；
     * Netty中的channelInactive()方法是通过Socket连接关闭时挥手数据包触发的，
     * 因此可以通过channelInactive()方法感知正常的下线情况，但是因为网络异常等非正常下线则无法感知；
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        log.info("client closed, channelId:{}", ctx.channel().id());
        // 添加到channelGroup 通道组
        WebSocketChannelHandler.channelGroup.remove(ctx.channel());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel channel = ctx.channel();
        ChannelId id = channel.id();
        // 首次连接是FullHttpRequest，处理参数
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest request = (FullHttpRequest) msg;
            String uri = request.uri();
            Map<String, String> paramMap = getUrlParams(uri);
            log.info("first creat channel, uri:{}, paramMap:{}, channelId:{}", uri, paramMap, id);
            // 如果url包含参数，需要处理
            if (uri.contains("?")) {
                String newUri = uri.substring(0, uri.indexOf("?"));
                System.out.println(newUri);
                request.setUri(newUri);
            }
        }
        if (msg instanceof TextWebSocketFrame) {
            // 正常的TEXT消息类型
            TextWebSocketFrame frame = (TextWebSocketFrame) msg;
            log.info("receive from client: " + frame.text());
            WebSocketChannelHandler.sendToAll(frame.text());
        }
        super.channelRead(ctx, msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame)
            throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error("WebSocketHandler exceptionCaught", cause);
        cause.printStackTrace();
        ctx.close();
    }

    private static Map<String, String> getUrlParams(String url) {
        Map<String, String> result = new HashMap<>();
        UrlBuilder builder = UrlBuilder.ofHttp(url, CharsetUtil.CHARSET_UTF_8);
        builder.getQuery().getQueryMap().forEach((k, v) -> {
            result.put(k.toString(), v.toString());
        });
        return result;
    }
}
