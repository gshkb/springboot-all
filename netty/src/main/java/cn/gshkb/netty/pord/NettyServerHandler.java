package cn.gshkb.netty.pord;

import cn.gshkb.netty.pord.message.HeartbeatResponsePacket;
import cn.gshkb.netty.protobuf.MessageBase;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@ChannelHandler.Sharable
public class NettyServerHandler extends SimpleChannelInboundHandler<MessageBase.Message> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageBase.Message message) throws Exception {
        if (message.getCmd().equals(MessageBase.Message.CommandType.HEARTBEAT_REQUEST)) {
            MessageBase.Message.parseFrom()
            log.info("收到客户端发来的心跳消息：{}", message.toString());
            channelHandlerContext.writeAndFlush(new HeartbeatResponsePacket());
        } else if (message.getCmd().equals(MessageBase.Message.CommandType.NORMAL)) {
            log.info("收到客户端的业务消息：{}", message.toString());
        }
    }
}
