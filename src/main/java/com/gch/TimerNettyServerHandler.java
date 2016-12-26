package com.gch;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;
import java.util.Date;

/**
 * Created by gch on 16-12-24.
 */
public class TimerNettyServerHandler extends ChannelHandlerAdapter {



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws IOException {
//        ctx.executor().scheduleAtFixedRate(new TimerNettyServerHandler.ServerTimerTask(ctx),0,5000, TimeUnit.MILLISECONDS);
    }





    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String)msg;
        if(message.charAt(0)=='@'){
            String mss = message.substring(1,message.length());
            System.out.println("receive from client : [ " + mss + " ]" +new Date());
            if(mss.equals("ping")){
                sendMessage(ctx, "@pong$");
            }
        }else{
            System.out.println("报文错误");
        }


    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private class ServerTimerTask implements Runnable {
        private final ChannelHandlerContext ctx;

        public ServerTimerTask(ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        public void run() {
            sendMessage(ctx,"@hhhh$");
        }


    }

    public void sendMessage(ChannelHandlerContext ctx,String s){
        byte[] bytes = s.getBytes();
        ByteBuf message = Unpooled.copiedBuffer(bytes);
        ctx.writeAndFlush(message);
    }
}
