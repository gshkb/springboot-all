package cn.gshkb.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 客户端消息处理
 *
 * @author hkb
 * @create 2019-08-24 17:49 v1.0
 **/
public class ClientStringHandler extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext handlerContext, Object msg) {
		System.out.println("client:" + msg.toString());
		handlerContext.writeAndFlush(msg.toString() + "___"+"客户端:你好");
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
