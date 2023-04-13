package com.ac.search.tool;

import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.springframework.stereotype.Component;

/**
 * @author Alan Chen
 * @description RestHighLevelClient DDL
 * @date 2023/4/8
 */
@Slf4j
@Component
public class EsClientSetting {

    /**
     * 创建索引setting
     * <p>
     * ngram分词器配置
     * ngram：英文单词按字母分词
     * field("filter","lowercase")：大小写兼容搜索
     * <p>
     * index.max_ngram_diff: 允许min_gram、max_gram的差值
     * https://www.elastic.co/guide/en/elasticsearch/reference/6.8/analysis-ngram-tokenizer.html
     * <p>
     * normalizer:解决keyword区分大小写
     * https://www.elastic.co/guide/en/elasticsearch/reference/6.0/normalizer.html
     * <p>
     * 拼音搜索
     * https://github.com/medcl/elasticsearch-analysis-pinyin
     * <p>
     * 简体繁华转换
     * https://github.com/medcl/elasticsearch-analysis-stconvert
     * <p>
     * 样例
     * https://blog.csdn.net/qq_39211866/article/details/85178707
     *
     * @return
     */
    public XContentBuilder packageSetting() {
        XContentBuilder setting = null;
        try {
            setting = XContentFactory.jsonBuilder()
                    .startObject()
                        .field("index.max_ngram_diff", "6")
                        .startObject("analysis")
                            .startObject("filter")
                                .startObject("edge_ngram_filter")
                                    .field("type", "edge_ngram")
                                    .field("min_gram", "1")
                                    .field("max_gram", "50")
                                .endObject()

                                .startObject("pinyin_edge_ngram_filter")
                                    .field("type", "edge_ngram")
                                    .field("min_gram", 1)
                                    .field("max_gram", 50)
                                .endObject()

                                //简拼
                                .startObject("pinyin_simple_filter")
                                    .field("type", "pinyin")
                                    .field("keep_first_letter", true)
                                    .field("keep_separate_first_letter", false)
                                    .field("keep_full_pinyin", false)
                                    .field("keep_original", false)
                                    .field("limit_first_letter_length", 50)
                                    .field("lowercase", true)
                                .endObject()

                                //全拼
                                .startObject("pinyin_full_filter")
                                    .field("type", "pinyin")
                                    .field("keep_first_letter", false)
                                    .field("keep_separate_first_letter", false)
                                    .field("keep_full_pinyin", true)
                                    .field("keep_original", false)
                                    .field("limit_first_letter_length", 50)
                                    .field("lowercase", true)
                                .endObject()

                            .endObject()

                            //简2繁
                            .startObject("char_filter")
                                .startObject("tsconvert")
                                    .field("type", "stconvert")
                                    .field("convert_type", "t2s")
                                .endObject()
                            .endObject()

                            .startObject("analyzer")
                                //模糊搜索、忽略大小写(按字符切分)
                                .startObject("ngram")
                                    .field("tokenizer", "my_tokenizer")
                                    .field("filter", "lowercase")
                                .endObject()

                                //ik+简体、繁体转换(ik最小切分)-用于查询关键字分词
                                .startObject("ikSmartAnalyzer")
                                    .field("type", "custom")
                                    .field("tokenizer", "ik_smart")
                                    .field("char_filter", "tsconvert")
                                .endObject()

                                //ik+简体、繁体转换(ik最大切分)-用于文档存储
                                .startObject("ikMaxAnalyzer")
                                    .field("type", "custom")
                                    .field("tokenizer", "ik_max_word")
                                    .field("char_filter", "tsconvert")
                                .endObject()

                                //简拼搜索
                                .startObject("pinyinSimpleIndexAnalyzer")
                                    .field("type", "custom")
                                    .field("tokenizer", "keyword")
                                    .array("filter", "pinyin_simple_filter", "pinyin_edge_ngram_filter", "lowercase")
                                .endObject()

                                //全拼搜索
                                .startObject("pinyinFullIndexAnalyzer")
                                    .field("type", "custom")
                                    .field("tokenizer", "keyword")
                                    .array("filter", "pinyin_full_filter", "lowercase")
                                .endObject()
                            .endObject()

                            .startObject("tokenizer")
                                .startObject("my_tokenizer")
                                    .field("type", "ngram")
                                    .field("min_gram", "1")
                                    .field("max_gram", "6")
                                .endObject()
                            .endObject()

                            .startObject("normalizer")
                                .startObject("lowercase")
                                    .field("type", "custom")
                                    .field("filter", "lowercase")
                                .endObject()
                            .endObject()
                        .endObject()
                    .endObject();
        } catch (Exception e) {
            log.error("setting构建失败");
            e.printStackTrace();
        }
        return setting;

    }
}
