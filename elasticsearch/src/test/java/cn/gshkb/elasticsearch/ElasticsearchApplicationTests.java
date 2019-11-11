package cn.gshkb.elasticsearch;


import cn.gshkb.elasticsearch.domain.Item;
import cn.gshkb.elasticsearch.respository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.metrics.avg.InternalAvg;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest(classes = ElasticsearchApplicationTests.class)
public class ElasticsearchApplicationTests {

    /*client测试用例*/
	/*private RestHighLevelClient client;

	@Before
	public void before(){
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

	*/
    /**
     * es 6.5官方 测试用例
     *//*
	@Test
	public void createIndexTwitter() throws IOException {
		CreateIndexRequest request = new CreateIndexRequest("twitter");
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
	}
	*/
    /*spring-data-es测试用例*/

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Autowired
    private ItemRepository itemRepository;

    @Before
    public void before() {
        // elasticsearchTemplate = new Con();
    }

    /**
     * 创建索引
     */
    @Test
    public void createIndex() {
        // 创建索引，会根据Item类的@Document注解信息来创建
        elasticsearchTemplate.createIndex(Item.class);
        // 配置映射，会根据Item类中的id、Field等字段来自动完成映射
        elasticsearchTemplate.putMapping(Item.class);
    }

    /**
     * 删除索引
     */
    @Test
    public void deleteIndex() {
        elasticsearchTemplate.deleteIndex("item");
    }

    /**
     * 新增
     */
    @Test
    public void insert() {
        Item item = new Item(1L, "小米手机7", "手机", "小米", 2999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg");
        itemRepository.save(item);
    }

    /**
     * 批量新增
     */
    @Test
    public void insertList() {
        List<Item> list = new ArrayList<>();
        list.add(new Item(2L, "坚果手机R1", "手机", "锤子", 3999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
        list.add(new Item(3L, "华为META20", "手机", "华为", 4999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
        list.add(new Item(4L, "iPhone X", "手机", "iPhone", 5100.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
        list.add(new Item(5L, "iPhone XS", "手机", "iPhone", 5999.00, "https://img12.360buyimg.com/n1/s450x450_jfs/t1/14081/40/4987/124705/5c371b20E53786645/c1f49cd69e6c7e6a.jpg"));
        // 接收对象集合，实现批量新增
        itemRepository.saveAll(list);
    }

    /**
     * 修改
     *
     * ：修改和新增是同一个接口，区分的依据就是id，这一点跟我们在页面发起PUT请求是类似的。
     */

    /**
     * 删除所有
     */
    @Test
    public void delete() {
        itemRepository.deleteAll();
    }

    /**
     * 基本查询
     */
    @Test
    public void query() {
        // 查询全部，并按照价格降序排序
        Iterable<Item> items = itemRepository.findAll(Sort.by("price").descending());
        items.forEach(item -> System.out.println("item = " + item));
    }

    /**
     * 自定义方法
     */
    @Test
    public void queryByPriceBetween() {
        // 根据价格区间查询
        List<Item> list = itemRepository.findByPriceBetween(5000.00, 6000.00);
        list.forEach(item -> System.out.println("item = " + item));
    }

    /**
     * 自定义查询
     */
    @Test
    public void search() {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.matchQuery("title", "小米手机"));
        // 搜索，获取结果
        Page<Item> items = itemRepository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("total = " + total);
        items.forEach(item -> System.out.println("item = " + item));
    }

    /**
     * 分页查询
     */
    @Test
    public void searchByPage() {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));
        // 分页：
        int page = 0;
        int size = 2;
        queryBuilder.withPageable(PageRequest.of(page, size));
        // 搜索，获取结果
        Page<Item> items = itemRepository.search(queryBuilder.build());
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);
        System.out.println("总页数 = " + items.getTotalPages());
        System.out.println("当前页：" + items.getNumber());
        System.out.println("每页大小：" + items.getSize());
        items.forEach(item -> System.out.println("item = " + item));
    }

    /**
     * 排序
     */
    @Test
    public void searchAndSort() {
        // 构建查询条件
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 添加基本分词查询
        queryBuilder.withQuery(QueryBuilders.termQuery("category", "手机"));
        // 排序
        queryBuilder.withSort(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        // 搜索，获取结果
        Page<Item> items = this.itemRepository.search(queryBuilder.build());
        // 总条数
        long total = items.getTotalElements();
        System.out.println("总条数 = " + total);
        items.forEach(item -> System.out.println("item = " + item));
    }

    /**
     * 聚合为桶
     */
    @Test
    public void testAgg() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(AggregationBuilders.terms("brands").field("brand"));
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) itemRepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称
            System.out.println(bucket.getKeyAsString());
            // 3.5、获取桶中的文档数量
            System.out.println(bucket.getDocCount());
        }
    }

    /**
     * 嵌套聚合，求平均值
     */
    @Test
    public void testSubAgg() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        // 不查询任何结果
        queryBuilder.withSourceFilter(new FetchSourceFilter(new String[]{""}, null));
        // 1、添加一个新的聚合，聚合类型为terms，聚合名称为brands，聚合字段为brand
        queryBuilder.addAggregation(
                AggregationBuilders.terms("brands").field("brand")
                        .subAggregation(AggregationBuilders.avg("priceAvg").field("price")) // 在品牌聚合桶内进行嵌套聚合，求平均值
        );
        // 2、查询,需要把结果强转为AggregatedPage类型
        AggregatedPage<Item> aggPage = (AggregatedPage<Item>) this.itemRepository.search(queryBuilder.build());
        // 3、解析
        // 3.1、从结果中取出名为brands的那个聚合，
        // 因为是利用String类型字段来进行的term聚合，所以结果要强转为StringTerm类型
        StringTerms agg = (StringTerms) aggPage.getAggregation("brands");
        // 3.2、获取桶
        List<StringTerms.Bucket> buckets = agg.getBuckets();
        // 3.3、遍历
        for (StringTerms.Bucket bucket : buckets) {
            // 3.4、获取桶中的key，即品牌名称  3.5、获取桶中的文档数量
            System.out.println(bucket.getKeyAsString() + "，共" + bucket.getDocCount() + "台");

            // 3.6.获取子聚合结果：
            InternalAvg avg = (InternalAvg) bucket.getAggregations().asMap().get("priceAvg");
            System.out.println("平均售价：" + avg.getValue());
        }
    }


}
