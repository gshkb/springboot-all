package cn.gshkb.netty.connection;

import cn.gshkb.netty.handler.ClientStringHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;


/**
 * @author hkb
 * @create 2019-08-24 17:41 v1.0
 **/
public class ImConnection {

	private Channel channel;

	public Channel connect(String host, int port) {
		doConnect(host, port);
		return this.channel;
	}

	private void doConnect(String host, int port) {
		Bootstrap bootstrap = new Bootstrap();

		EventLoopGroup workers = new NioEventLoopGroup();
		bootstrap.group(workers);
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
		bootstrap.handler(new ChannelInitializer<SocketChannel>() {
			@Override
			protected void initChannel(SocketChannel socketChannel) throws Exception {
				socketChannel.pipeline().addLast("decoder", new StringDecoder());
				socketChannel.pipeline().addLast("encoder", new StringEncoder());
				socketChannel.pipeline().addLast(new ClientStringHandler());
			}
		});

		ChannelFuture future = null;
		try {
			future = bootstrap.connect(host, port).sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		channel = future.channel();

	}
}
