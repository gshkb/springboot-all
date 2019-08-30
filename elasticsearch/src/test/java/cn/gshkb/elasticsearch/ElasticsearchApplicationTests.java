package cn.gshkb.elasticsearch;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.get.GetIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.IndicesOptions;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.json.JsonXContent;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@Configuration
public class ElasticsearchApplicationTests {


	private RestHighLevelClient client;

	@Before
	public void before() {
		client = new RestHighLevelClient(
				RestClient.builder(
						new HttpHost("localhost", 9200, "http")));
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void createIndex() throws IOException {


		CreateIndexRequest request = new CreateIndexRequest("pybbs");
		request.settings(Settings.builder()
				.put("index.number_of_shards", 1)
				.put("index.number_of_shards", 5));

		CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);

		log.info(response.toString());
		System.out.println(response.isAcknowledged()); // 索引已经存在，则报错
	}

	@Test
	public void createIndexWithMapping() throws IOException {


		CreateIndexRequest request = new CreateIndexRequest("pybbs");
		request.settings(Settings.builder()
				.put("index.number_of_shards", 1)
				.put("index.number_of_shards", 5));

		XContentBuilder mappingBuilder = JsonXContent.contentBuilder()
				.startObject()
				.startObject("properties")
				.startObject("title")
				.field("type", "text")
				.field("analyzer", "ik_max_word")
				.field("index", "true")
				.endObject()
				.startObject("content")
				.field("type", "text")
				.field("analyzer", "ik_max_word") // ik_max_word 这个分词器是ik的，可以去github上搜索安装es的ik分词器插件
				.field("index", "true")
				.endObject()
				.endObject()
				.endObject();
		request.mapping("topic", mappingBuilder);

		CreateIndexResponse response = client.indices().create(request, RequestOptions.DEFAULT);

		log.info(response.toString());
		System.out.println(response.isAcknowledged());
	}

	@Test
	public void existIndex() throws IOException {


		GetIndexRequest request = new GetIndexRequest();
		request.indices("pybbs");
		request.local(false);
		request.humanReadable(true);

		boolean exists = client.indices().exists(request, RequestOptions.DEFAULT);

		log.info("result: {}", exists);
	}

	@Test
	public void deleteIndex() throws IOException {

		DeleteIndexRequest request = new DeleteIndexRequest("pybbs");
		request.indicesOptions(IndicesOptions.LENIENT_EXPAND_OPEN);

		AcknowledgedResponse response = client.indices().delete(request, RequestOptions.DEFAULT);

		log.info("result: {}", response.isAcknowledged());
	}

	@Test
	public void createDocument() throws IOException {

		Map<String, Object> map = new HashMap<>();
		map.put("title", "上海自来水来自海上测测测啊");

		IndexRequest request = new IndexRequest("pybbs", "topic"); // 这里最后一个参数是es里储存的id，如果不填，es会自动生成一个，个人建议跟自己的数据库表里id保持一致，后面更新删除都会很方便
		request.source(map);
		IndexResponse response = client.index(request, RequestOptions.DEFAULT);
		// not exist: result: code: 201, status: CREATED
		// exist: result: code: 200, status: OK
		log.info("result: code: {}, status: {}", response.status().getStatus(), response.status().name());
	}

	@Test
	public void searchDocument() throws IOException {

		SearchRequest       request = new SearchRequest("pybbs");
		SearchSourceBuilder builder = new SearchSourceBuilder();
		builder.query(QueryBuilders.matchQuery("title", "江大桥来自中国从武汉到上海喝中国人的自来水"));
		// builder.from(0).size(2); // 分页
		request.source(builder);

		SearchResponse response = client.search(request, RequestOptions.DEFAULT);
		log.info(response.toString(), response.getTotalShards());
		for (SearchHit documentFields : response.getHits()) {
			log.info("result: {}, code: {}, status: {}", documentFields.toString(), response.status().getStatus(), response.status().name());
		}
	}

	/**
	 * es 6.5官方 测试用例
	 */
	@Test
	public void createIndexTwitter() throws IOException {
		CreateIndexRequest request = new CreateIndexRequest("twitter");
		request.settings(Settings.builder()
				.put("index.number_of_shards", 1)
				.put("index.number_of_shards", 5)
				.put("number_of_replicas", 2));

		XContentBuilder mappingBuilder = JsonXContent.contentBuilder()
				.startObject()
				.startObject("properties")
				.startObject("title")
				.field("type", "text")
				.field("analyzer", "ik_max_word")
				.field("index", "true")
				.endObject()
				.startObject("content")
				.field("type", "text")
				.field("analyzer", "ik_max_word") // ik_max_word 这个分词器是ik的，可以去github上搜索安装es的ik分词器插件
				.field("index", "true")
				.endObject()
				.endObject()
				.endObject();
		request.mapping("topic", mappingBuilder);
	}

	@Test
	public void createIndexByAli() throws IOException {
		CreateIndexRequest request = new CreateIndexRequest("test");
		Alias              alias   = new Alias("term");
		alias.filter("\"term\" : {\"user\" : \"kimchy\" }");
		alias.routing("kimchy");
		request.alias(alias);

	}

	@Test
	public void createIndexByTempate() throws IOException {
		CreateIndexRequest request = new CreateIndexRequest("test");
		new CreateIndexRequest();

	}
}
