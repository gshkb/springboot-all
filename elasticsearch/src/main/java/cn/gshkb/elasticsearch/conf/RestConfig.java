package cn.gshkb.elasticsearch.conf;

import org.apache.http.*;
import org.elasticsearch.client.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;

import java.security.*;

/**
 * @author hkb
 * @create 2019-08-02 19:53 v1.0
 **/
@Configuration
public class RestConfig {
	@Bean
	@Qualifier("client")
	public RestClient getClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		// 如果有多个从节点可以持续在内部new多个HttpHost，参数1是ip,参数2是HTTP端口，参数3是通信协议
		RestClientBuilder clientBuilder = RestClient.builder(new HttpHost("localhost", 9200, "http"));

		// 添加其他配置，返回来的还是RestClientBuilder对象，这些配置都是可选的
		// clientBuilder.setXX()...

		// 最后配置好的clientBuilder再build一下即可得到真正的Client
		return clientBuilder.build();
	}

	@Bean
	@Qualifier("highLevelClient")
	public RestHighLevelClient getHighClient() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
		// 如果有多个从节点可以持续在内部new多个HttpHost，参数1是ip,参数2是HTTP端口，参数3是通信协议
		return new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("localhost", 9200, "http")));

	}
}
