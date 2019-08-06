#springboot 整合es
 
 
 springboot整合es有三种方式:
 
* api整合
  
  主要的依赖:
     ```xml
    		 <dependency>
                  <groupId>org.elasticsearch.client</groupId>
                  <artifactId>transport</artifactId>
                  <version>${elasticsearch.version}</version>
             </dependency>
          	<!--ES在官方网站公告 TransportClient es8.0版本以后 将被移除.
       		猜测主要原因:第一存在并发瓶颈,第二,采用tcp连接,版本兼容差.-->
	
* 使用REST Clinet (推荐) 

  主要的依赖:
     ```xml
    		<dependency>
                <groupId>org.elasticsearch.client</groupId>
                <artifactId>elasticsearch-rest-client</artifactId>
                <version>${elasticsearch.version}</version>
            </dependency>
* 使用 spring-data-elasticsearch

   主要的依赖:
   ```xml

	   <dependency>
	        <groupId>org.springframework.data</groupId>
	        <artifactId>spring-data-elasticsearch</artifactId>
	        <version>${elasticsearch.version}</version>
	  </dependency>  
 个人微信 hkb1888 	        
 
 
  