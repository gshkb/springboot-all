package cn.gshkb.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 消息处理
 *
 * @author hkb
 * @create 2019-08-24 17:31 v1.0
 **/
public class ServerStringHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx,Object msg){
		System.out.println("server:" + msg.toString());
		//ctx.writeAndFlush(msg.toString() + "_____"+"服务端:你好");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
