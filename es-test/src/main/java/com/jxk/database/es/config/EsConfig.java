package com.jxk.database.es.config;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchEntityMapper;
import org.springframework.data.elasticsearch.core.EntityMapper;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.http.HttpHeaders;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;

import java.time.Duration;

/**
 * @description:
 * @author: jxk
 * @create: 2020-03-31 09:50
 **/
@Configuration
@EnableElasticsearchRepositories(basePackages = "com.jxk.database.es.repository")
public class EsConfig extends AbstractElasticsearchConfiguration {

    @Override
    public RestHighLevelClient elasticsearchClient() {
        /*HttpHeaders defaultHeaders = new HttpHeaders();
        defaultHeaders.setBasicAuth("elastic", "123456");*/


        ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo("120.55.166.9:9200", "")
                .withConnectTimeout(Duration.ofSeconds(5))
                .withSocketTimeout(Duration.ofSeconds(3))
               // .withDefaultHeaders(defaultHeaders)

                .withBasicAuth("elastic", "123456")
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
/*    @Bean
    public RestHighLevelClient client() {
        //用户认证对象
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        //设置账号密码
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials("elastic", "654321"));
       // 创建rest client对象
        RestClientBuilder builder = RestClient.builder(
                new HttpHost("192.168.20.57", 9200),
                new HttpHost("192.168.20.58", 9200),
                new HttpHost("192.168.20.59", 9200))
                .setHttpClientConfigCallback(new RestClientBuilder.HttpClientConfigCallback() {
                    @Override
                    public HttpAsyncClientBuilder customizeHttpClient(HttpAsyncClientBuilder httpAsyncClientBuilder) {
                        return httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
                    }
                });
        return new RestHighLevelClient(builder);
    }*/



   /* @Bean
    @Override
    public EntityMapper entityMapper() {

        ElasticsearchEntityMapper entityMapper = new ElasticsearchEntityMapper(
                elasticsearchMappingContext(), new DefaultConversionService()
        );
        entityMapper.setConversions(elasticsearchCustomConversions());

        return entityMapper;
    }*/

}
