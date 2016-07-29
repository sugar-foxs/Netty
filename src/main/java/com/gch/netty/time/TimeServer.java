package com.gch.netty.time;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created by gch on 16-7-28.
 */
public class TimeServer {
    public void bind(int port) throws Exception{
        //配置服务端的NIO线程组
        EventLoopGroup boosGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try{
            //辅助启动类
            ServerBootstrap b = new ServerBootstrap();
            b.group(boosGroup,workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG,100)
                    .childHandler(new ChildChannelHandler());

            //绑定端口，同步等待成功
            ChannelFuture f = b.bind(port).sync();
            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        }finally {
            workerGroup.shutdownGracefully();
            boosGroup.shutdownGracefully();
        }
    }

    private class ChildChannelHandler extends ChannelInitializer<SocketChannel>{

        @Override
        public void initChannel(SocketChannel socketChannel) throws Exception {
            //LineBasedFrameDecoder(1024)，StringDecoder()用来解决TCP粘包/拆包问题
            //socketChannel.pipeline().addLast(new LineBasedFrameDecoder(1024));

            //分割符
            //ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
            //socketChannel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,delimiter));

            //定长
            socketChannel.pipeline().addLast(new FixedLengthFrameDecoder(32));
            socketChannel.pipeline().addLast(new StringDecoder());
            socketChannel.pipeline().addLast(new TimeServerHandler());
        }
    }

    public static void main(String[] args) throws Exception {
        int port = 8080;
        if (args !=null && args.length > 0) {
            port = Integer.parseInt(args[0]);
        }
        new TimeServer().bind(port);
    }
}
