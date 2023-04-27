package com.ac.gateway.authentication;

import cn.hutool.core.net.URLEncoder;
import com.alibaba.fastjson.JSONObject;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

public class Oauth2AuthSuccessHandler implements ServerAuthenticationSuccessHandler {
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        MultiValueMap<String, String> headerValues = new LinkedMultiValueMap(4);
        Object principal = authentication.getPrincipal();
        //客户端模式只返回一个clientId
        if (principal instanceof UserDetails) {
            UserDetails user = (UserDetails) authentication.getPrincipal();
            headerValues.add("uname", user.getUsername());
            headerValues.add("user", URLEncoder.DEFAULT.encode(JSONObject.toJSONString(user), Charset.defaultCharset()));
        }

        ServerWebExchange exchange = webFilterExchange.getExchange();
        ServerHttpRequest serverHttpRequest = exchange.getRequest().mutate()
                .headers(h -> {
                    h.addAll(headerValues);
                })
                .build();

        ServerWebExchange build = exchange.mutate().request(serverHttpRequest).build();
        return webFilterExchange.getChain().filter(build);
    }
}
