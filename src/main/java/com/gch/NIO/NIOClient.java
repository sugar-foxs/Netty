package com.gch.NIO;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.net.InetAddress;

/**
 * TCP/IP的NIO非阻塞方式
 * 客户端
 * */
public class NIOClient {

    //创建缓冲区

    //访问服务器

    public void query(String host, int port) throws IOException, InterruptedException {
        InetSocketAddress address = new InetSocketAddress(InetAddress.getByName(host), port);
        SocketChannel socket=null;
//        for(int i=0;i<100;i++){
//            try{
//                socket = SocketChannel.open();
//                socket.connect(address);
//                buffer.putInt(i);
//                buffer.flip();
//                socket.write(buffer);
//                buffer.clear();
//                Thread.sleep(100);
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }

        socket = SocketChannel.open();
        socket.connect(address);
        while (true) {
            try {
                byte[] bytes = new byte[512];
                System.in.read(bytes);

//                buffer.clear();
//                buffer.put(bytes);
//                buffer.flip();
//                socket.write(buffer);
//                buffer.clear();
                ByteBuffer buffer = ByteBuffer.wrap(bytes);
                socket.write(buffer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new NIOClient().query("localhost", 8080);

    }
}