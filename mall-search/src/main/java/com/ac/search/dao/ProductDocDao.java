package com.ac.search.dao;

import com.ac.search.entity.ProductDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductDocDao extends ElasticsearchRepository<ProductDoc,String> {

}
