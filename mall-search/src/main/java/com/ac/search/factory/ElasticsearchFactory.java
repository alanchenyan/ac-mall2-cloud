package com.ac.search.factory;

import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;

/**
 * @author: lujie
 * @create: 2021/4/12
 * @description: ES 对象工厂
 **/

public class ElasticsearchFactory {

    public static HighlightBuilder builderHighlight() {
        HighlightBuilder highlightBuilder = new HighlightBuilder()
                .preTags("<span style='color:red'>")
                .postTags("</span>")
                .field("*")
                //关闭多个高亮
                .requireFieldMatch(false);
        return highlightBuilder;
    }
}
