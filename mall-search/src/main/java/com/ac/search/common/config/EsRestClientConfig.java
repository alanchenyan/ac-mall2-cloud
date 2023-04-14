package com.ac.search.common.config;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.RestClients;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories
public class EsRestClientConfig extends AbstractElasticsearchConfiguration {

    @Value("${es.url}")
    private String esUrl;

    @Override
    @Bean
    public RestHighLevelClient elasticsearchClient() {
        System.out.println(esUrl);
        final ClientConfiguration clientConfiguration = ClientConfiguration.builder()
                .connectedTo(esUrl)
                .build();

        return RestClients.create(clientConfiguration).rest();
    }
}
