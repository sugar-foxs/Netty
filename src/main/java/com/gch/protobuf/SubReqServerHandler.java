package com.gch.protobuf;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Created by gch on 16-12-23.
 */
public class SubReqServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq)msg;
        if("Lilinfeng".equalsIgnoreCase(req.getUserName())){
            System.out.println("Server accept client subscribe req :[" +req.toString()+"]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }



    private SubscribeRespProto.SubscribeResp resp(int subReqID){
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubRespID(subReqID);
        builder.setRespCode(0);
        builder.setDesc("Netty book order succeed,3 days later,send to the designated address");
        return builder.build();
    }

    public void execptionCaught(ChannelHandlerContext ctx,Throwable cause){
        cause.printStackTrace();
        ctx.close();
    }
}
