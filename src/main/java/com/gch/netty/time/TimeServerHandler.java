package com.gch.netty.time;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by gch on 16-7-28.
 */
public class TimeServerHandler extends ChannelHandlerAdapter {
    private int counter=0;
    //对网络事件进行读写操作
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {


        System.out.println("receive client : [ " + msg + "]");

//        String body = (String)msg;
//
//        System.out.println("This is "+ ++counter+" times receive client:[ "+body+" ]");
//
//        body +="$_";
//
//        ByteBuf echo = Unpooled.copiedBuffer(body.getBytes());
//
//        ctx.write(echo);



//        System.out.println("the timeserver receive order ："+ body+" ;the counter is :"+ (++counter));
//
//        String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body)?new java.util.Date(System.currentTimeMillis()).toString():"BAD ORDER";
//
//        currentTime = currentTime + System.getProperty("line.separator");
//
//        ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());

        //异步发送应答消息到缓冲数组中，再调用flush()
        //ctx.write(resp);

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将缓冲数组中的消息写到SocketChannel中
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
