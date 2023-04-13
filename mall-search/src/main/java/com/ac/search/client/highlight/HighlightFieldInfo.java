package com.ac.search.client.highlight;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HighlightFieldInfo {

    private String name;

    private boolean fragment;

    private String text;
}
