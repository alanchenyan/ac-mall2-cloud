package com.ac.core.enums;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Locale;

/**
 * 多语言类型枚举
 *
 * @author Alan Chen
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(description = "多语言类型枚举")
public enum LanguageEnum {
    zh_CN("zh_CN", "简体中文", new Locale("zh", "CN")),
    zh_HK("zh_HK", "繁體中文", new Locale("zh", "HK")),
    en_US("en_US", "English", new Locale("en", "US"));

    /**
     * 语言代码
     */
    private String code;
    /**
     * 语言名称
     */
    private String name;
    /**
     * Locale
     */
    private Locale locale;

    /**
     * 解析
     *
     * @param type
     * @return
     */
    public static LanguageEnum parse(String type) {
        return parse(type, null);
    }

    /**
     * 解析
     *
     * @param type
     * @param dau
     * @return
     */
    public static LanguageEnum parse(String type, LanguageEnum dau) {
        if (null != type && !type.isEmpty()) {
            try {
                return LanguageEnum.valueOf(type);
            } catch (IllegalArgumentException e) {
            }
        }
        return dau;
    }
}
