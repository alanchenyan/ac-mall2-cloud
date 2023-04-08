package com.ac.search.highlight;

import lombok.Data;

import java.util.Map;

@Data
public class BaseHighlight {

    private Map<String, HighlightFieldInfo> highlightFields;
}
