package com.ac.order.component;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.HttpCookie;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class XxlJobComponent {

    private final Map<String, String> loginCookie = new HashMap<>();

    /**
     * xxl-job登录
     *
     * @return
     */
    public String login() {
        String adminAddresses = "http://127.0.0.1:8081/xxl-job-admin";
        String url = adminAddresses + "/login";
        String username = "admin";
        String password = "123456";
        HttpResponse response = HttpRequest.post(url)
                .form("userName", username)
                .form("password", password)
                .execute();
        List<HttpCookie> cookies = response.getCookies();
        Optional<HttpCookie> cookieOpt = cookies.stream()
                .filter(cookie -> cookie.getName().equals("XXL_JOB_LOGIN_IDENTITY")).findFirst();
        if (!cookieOpt.isPresent())
            throw new RuntimeException("get xxl-job cookie error!");

        String value = cookieOpt.get().getValue();
        loginCookie.put("XXL_JOB_LOGIN_IDENTITY", value);

        log.info("XxlJobComponent.login.token={}", value);
        return value;
    }

    /**
     * 其他接口在调用时，直接从缓存中获取cookie，如果缓存中不存在则调用/login接口，为了避免这一过程失败，允许最多重试3次
     *
     * @return
     */
    public String getCookie() {
        for (int i = 0; i < 3; i++) {
            String cookieStr = loginCookie.get("XXL_JOB_LOGIN_IDENTITY");
            if (cookieStr != null) {
                return "XXL_JOB_LOGIN_IDENTITY=" + cookieStr;
            }
            login();
        }
        throw new RuntimeException("get xxl-job cookie error!");
    }
}
