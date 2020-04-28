package cn.gshkb.netty;

import cn.gshkb.netty.connection.ImConnection;
import cn.gshkb.netty.server.ImServer;
import io.netty.channel.Channel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyApplication {

	public static void main(String[] args) {
		SpringApplication.run(NettyApplication.class, args);
		//启动服务端
		int port =2222;
		//启动客户端
		String host = "127.0.0.1";
		Channel connect = new ImConnection().connect(host, port);
		connect.writeAndFlush("holle world!");

		new Thread(()->{
			new ImServer().run(port);
		}).start();
	}

}
