package com.gch.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by gch on 16-7-28.
 */
public class TimeClientHandler extends ChannelHandlerAdapter {

    private int counter;
//    private byte[] req;

    static final String ECHO_REQ = "Hi,guchunhui,Welcome to Netty.$_";

    public TimeClientHandler() {

//        req = ("QUERY TIME ORDER"+System.getProperty("line.separator")).getBytes();

    }

    //客户端发送消息
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {

//        ByteBuf message = null;
//        for(int i=0;i<100;i++){
//            message = Unpooled.buffer(req.length);
//            message.writeBytes(req);
//            ctx.writeAndFlush(message);

//        }
        for(int i=0;i<10;i++){
            ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
        }
    }

    //客户端接收消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

//        String body = (String)msg;
//
//        System.out.println("Now is :"+ body+" ;the counter is :"+(++counter));
        System.out.println("This is "+ ++counter +" times receive server:[ "+msg+" ]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        ctx.close();
    }
}
