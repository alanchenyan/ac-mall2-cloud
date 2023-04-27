package com.ac.gateway.authentication;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import com.ac.core.redis.util.RdsComponent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class PermissionAuthManager implements ReactiveAuthorizationManager<AuthorizationContext> {

    @Resource
    private RdsComponent rdsComponent;

    @Override
    public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext authorizationContext) {
        return authentication.map(auth -> {
            ServerWebExchange exchange = authorizationContext.getExchange();
            ServerHttpRequest request = exchange.getRequest();
            boolean isPermission = hasPermission(auth, request.getMethodValue(), request.getURI().getPath());
            return new AuthorizationDecision(isPermission);
        }).defaultIfEmpty(new AuthorizationDecision(false));
    }

    public boolean hasPermission(Authentication authentication, String requestMethod, String requestURI) {
        // 前端跨域OPTIONS请求预检放行 也可通过前端配置代理实现
        if (HttpMethod.OPTIONS.name().equalsIgnoreCase(requestMethod)) {
            return true;
        }
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            //判断是否开启url权限验证
            Object obj = rdsComponent.hGet("AUTH:RESOURCE:PERMS_MAP", requestURI);
            if (null == obj) {
                return true;
            }

            List<String> authorities = new ArrayList<>();
            List<String> dbAuthorities = Convert.toList(String.class, obj);
            for (String auth : dbAuthorities) {
                if (auth.contains(requestMethod)) {
                    authorities.add(auth);
                    break;
                }
            }

            if (authorities.isEmpty()) {
                log.info("没有找到该接口的权限配置，该路径不需要控制权限: URI: " + requestURI);
                return true;
            }

            List<SimpleGrantedAuthority> grantedAuthorityList = (List<SimpleGrantedAuthority>) authentication.getAuthorities();
            if (CollectionUtil.isEmpty(grantedAuthorityList)) {
                log.warn("角色列表为空：{}", authentication.getPrincipal());
                return false;
            }
            return grantedAuthorityList.stream().anyMatch(simpleGrantedAuthority -> authorities.contains(simpleGrantedAuthority.getAuthority()));
        }
        return false;
    }
}
