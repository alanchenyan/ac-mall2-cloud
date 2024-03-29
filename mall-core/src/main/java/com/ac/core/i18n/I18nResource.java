package com.ac.core.i18n;

import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * @author Alan Chen
 * @description
 * @date 2023/4/27
 */
public class I18nResource {

    private final String ZH_CN = "CN";
    private final String ZH_TW = "TW";
    private final String EN_US = "US";

    protected Locale defaultLocale(){
        return Locale.SIMPLIFIED_CHINESE;
    }

    protected ResourceBundleMessageSource resourceBundleMessageSource;
    private Object[] objs = {"language"};

    public I18nResource(String path) {
        resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setDefaultEncoding(StandardCharsets.UTF_8.name());
        resourceBundleMessageSource.setBasename(path);
    }

    public String getValue(String key, HttpServletRequest request) {
        String value = "";
        try {
            if(request == null){
                return value;
            }
            String lang = request.getHeader("Content-Language");
            Locale customerLocale;
            if (StringUtils.isEmpty(lang)) {
                customerLocale = request.getLocale();
            } else {
                String upperLang = lang.toUpperCase();
                if (upperLang.contains(ZH_TW)) {
                    customerLocale = Locale.TRADITIONAL_CHINESE;
                } else if (upperLang.contains(ZH_CN)) {
                    customerLocale = Locale.SIMPLIFIED_CHINESE;
                } else if (upperLang.contains(EN_US)) {
                    customerLocale = Locale.US;
                } else {
                    customerLocale = defaultLocale();
                }
            }
            value = getValue(key, customerLocale);
        } catch (Exception e) {
            value = getValue(key, defaultLocale());
        }
        return value;
    }

    public String getValue(String key) {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        return getValue(key,request);
    }

    public String getValue(String keyValue, Locale locale) {
        if (resourceBundleMessageSource != null) {
            return resourceBundleMessageSource.getMessage(keyValue, objs,
                    locale);
        }
        return "";
    }
}
