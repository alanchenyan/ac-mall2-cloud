package com.ac.core.i18n;

/**
 * @author Alan Chen
 * @description
 * @date 2023/4/27
 */
public class I18nResourceFactory {

    private final static I18nResource I18NRESOURCE = new I18nResource("i18n/response");

    private I18nResourceFactory(){
    }

    public static I18nResource getI18nResource(){
        return I18NRESOURCE;
    }
}
