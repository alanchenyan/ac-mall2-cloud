package com.ac.search.template.dao;

import com.ac.search.template.entity.OrderDoc;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface OrderDocDao extends ElasticsearchRepository<OrderDoc,String> {

}
