package com.ac.search.mapping;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ProductDocMapping {

    public XContentBuilder packageMapping() {
        XContentBuilder mapping = null;
        try {
            //创建索引Mapping
            mapping = XContentFactory.jsonBuilder()
                    .startObject()
                        .field("dynamic", true)
                        .startObject("properties")
                            //id
                            .startObject("id")
                                .field("type", "long")
                                .field("index", false)
                            .endObject()

                            //分类(关键字)
                            .startObject("category")
                                .field("type", "keyword")
                            .endObject()

                            //品牌(关键字)
                            .startObject("brand")
                                .field("type", "keyword")
                            .endObject()

                            //产品名称(模糊搜索)
                            .startObject("productName")
                                .field("type", "text")
                                .field("analyzer", "ngram")
                                .field("search_analyzer", "ikSmartAnalyzer")
                                .startObject("fields")
                                    .startObject("pinyin")
                                        .field("type", "text")
                                        .field("index", true)
                                        .field("analyzer", "pinyinFullIndexAnalyzer")
                                    .endObject()
                                .endObject()
                            .endObject()

                            //产品详情描述(分词搜索)
                            .startObject("remark")
                                .field("type", "text")
                                .field("analyzer", "ikMaxAnalyzer")
                                .field("search_analyzer", "ikSmartAnalyzer")
                                .startObject("fields")
                                    .startObject("pinyin")
                                        .field("type", "text")
                                        .field("index", true)
                                        .field("analyzer", "pinyinFullIndexAnalyzer")
                                    .endObject()
                                .endObject()
                            .endObject()

                        .endObject()
                    .endObject();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return mapping;
    }
}
