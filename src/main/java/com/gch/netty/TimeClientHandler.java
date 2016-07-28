package com.gch.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by gch on 16-7-28.
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

    private final ByteBuf clientMessage;


    public TimeClientHandler() {

        byte [] req = "QUERY TIME ORDER".getBytes();
        clientMessage = Unpooled.buffer(req.length);
        clientMessage.writeBytes(req);
    }

    //客户端发送消息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

        ctx.writeAndFlush(clientMessage);
        System.out.println("TimeClient post Message："+ "QUERY TIME ORDER" );
    }

    //客户端接收消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf buf = (ByteBuf)msg;
        byte [] req = new byte[buf.readableBytes()];

        buf.readBytes(req);

        String body = new String(req,"UTF-8");

        System.out.println("Now is :"+ body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        ctx.close();
    }
}
