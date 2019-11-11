package cn.gshkb.netty.server;

import cn.gshkb.netty.handler.ServerStringHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * @author hkb
 * @create 2019-08-24 17:13 v1.0
 **/
public class ImServer {

	public void run(int port) {

		ServerBootstrap bootstrap = new ServerBootstrap();
		EventLoopGroup  boss      = new NioEventLoopGroup();
		EventLoopGroup  woker     = new NioEventLoopGroup();

		ServerBootstrap serverBootstrap = bootstrap.group(boss, woker)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel socketChannel) throws Exception {
						socketChannel.pipeline().addLast("decoder", new StringDecoder());
						socketChannel.pipeline().addLast("encoder", new StringEncoder());
						socketChannel.pipeline().addLast(new ServerStringHandler());
					}
				})
				.option(ChannelOption.SO_BACKLOG, 128)
				.childOption(ChannelOption.SO_KEEPALIVE, true);

		try {
			ChannelFuture f = bootstrap.bind(port).sync();
			f.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally {
			woker.shutdownGracefully();
			boss.shutdownGracefully();
		}

	}
}
