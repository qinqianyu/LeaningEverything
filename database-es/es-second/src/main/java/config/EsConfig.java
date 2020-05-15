package config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

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

    @Bean
    ElasticsearchOperations elasticsearchTemplate() throws Exception {
        ElasticsearchRestTemplate restTemplate = new ElasticsearchRestTemplate(elasticsearchClient());
        return restTemplate;
    }

}
