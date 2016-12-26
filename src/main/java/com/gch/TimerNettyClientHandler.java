package com.gch;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;

import java.io.IOException;
import java.util.Date;
import java.util.TimerTask;

/**
 * Created by gch on 16-12-24.
 */
public class TimerNettyClientHandler extends ChannelHandlerAdapter {


    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws IOException {

//        ctx.executor().scheduleAtFixedRate(new TimerNettyClientHandler.ClientTimerTask(ctx),0,5000, TimeUnit.MILLISECONDS);
    }



    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        String message = (String)msg;
        if(message.charAt(0)=='@'){
            System.out.println("receive from server : [ " + message.substring(1,message.length()) + " ]" +new Date());
        }else{
            System.out.println("报文错误");
        }
    }


    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (IdleStateEvent.class.isAssignableFrom(evt.getClass())) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                System.out.println("read idle");
                sendMessage(ctx, "@ping$");
            }else if (event.state() == IdleState.WRITER_IDLE) {
                System.out.println("write idle");
            } else if (event.state() == IdleState.ALL_IDLE){
                System.out.println("all idle");
            }
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

    private class ClientTimerTask extends TimerTask {

        private final ChannelHandlerContext ctx;

        public ClientTimerTask(final ChannelHandlerContext ctx) {
            this.ctx = ctx;
        }

        public void run() {
            sendMessage(ctx,"@gggg$");
        }
    }

    public void sendMessage(ChannelHandlerContext ctx,String s){
        byte[] bytes = s.getBytes();
        ByteBuf message = Unpooled.copiedBuffer(bytes);
        ctx.writeAndFlush(message);
    }


}
