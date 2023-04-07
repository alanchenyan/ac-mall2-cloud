package com.ac.search.dao;

import com.ac.search.entity.OrderDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OrderDocDao extends ElasticsearchRepository<OrderDoc,String> {

}
