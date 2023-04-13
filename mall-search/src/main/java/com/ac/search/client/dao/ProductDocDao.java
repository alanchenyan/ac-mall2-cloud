package com.ac.search.client.dao;

import com.ac.search.client.entity.ProductDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductDocDao extends ElasticsearchRepository<ProductDoc,String> {

}
