package com.bird.say.hi.link.handler;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * @author zhangruilong <zhangruilong03@kuaishou.com>
 * Created on 2024-03-07
 */
public class WebSocketChannelHandler {
    public WebSocketChannelHandler() {
    }

    public static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    private static ConcurrentMap<String, ChannelId> ChannelMap = new ConcurrentHashMap();

    public static void addChannel(String apiToken, Channel channel) {
        channelGroup.add(channel);
        if (null != apiToken) {
            ChannelMap.put(apiToken, channel.id());
        }
    }

    public static void updateChannel(String apiToken, Channel channel) {
        Channel chan = channelGroup.find(channel.id());
        if (null == chan) {
            addChannel(apiToken, channel);
        } else {
            ChannelMap.put(apiToken, channel.id());
        }
    }

    public static void removeChannel(Channel channel) {
        channelGroup.remove(channel);
        channel.close();
        Collection<ChannelId> values = ChannelMap.values();
        values.remove(channel.id());
    }

    public static Channel findChannel(String apiToken) {
        ChannelId chanId = ChannelMap.get(apiToken);
        if (null == chanId) {
            return null;
        }
        return channelGroup.find(ChannelMap.get(apiToken));
    }

    public static void sendToAll(String message) {
        channelGroup.writeAndFlush(new TextWebSocketFrame(message));
    }

    //给每个人发送消息,除发消息人外
    private void SendAllExceptMy(String apiToken, String msg) {
        Channel myChannel = channelGroup.find(ChannelMap.get(apiToken));
        if(null != myChannel){
            for(Channel channel:channelGroup){
                if(!channel.id().asLongText().equals(myChannel.id().asLongText())){
                    channel.writeAndFlush(new TextWebSocketFrame(msg));
                }
            }
        }
    }

    public static void sendToSimple(String apiToken, String message) {
        channelGroup.find(ChannelMap.get(apiToken)).writeAndFlush(new TextWebSocketFrame(message));
    }
}
