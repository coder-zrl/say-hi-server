package com.bird.say.hi.link.handler;

import java.util.Date;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

/**
 * @author zhangruilong <zhangruilong03@kuaishou.com>
 * Created on 2024-03-07
 */
public class HeartBeatHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object obj){
        if (obj instanceof IdleStateEvent){
            IdleStateEvent event = (IdleStateEvent)obj;
            if (event.state()== IdleState.READER_IDLE){
                System.out.println(ctx.channel().id() +"客户端读超时" + new Date());
                WebSocketChannelHandler.removeChannel(ctx.channel());
            }else if (event.state()== IdleState.WRITER_IDLE){
                System.out.println(ctx.channel().id() +"客户端写超时" + new Date());
            }else if (event.state()== IdleState.ALL_IDLE){
                System.out.println(ctx.channel().id() +"客户端所有操作超时");
            }
        }
    }
}
