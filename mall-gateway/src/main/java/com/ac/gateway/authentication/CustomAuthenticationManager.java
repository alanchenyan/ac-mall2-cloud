package com.ac.gateway.authentication;

import com.ac.core.exception.ServerException;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import reactor.core.publisher.Mono;

public class CustomAuthenticationManager implements ReactiveAuthenticationManager {
    private TokenStore tokenStore;

    public CustomAuthenticationManager(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
    }

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        return Mono.justOrEmpty(authentication)
                .filter(a -> a instanceof BearerTokenAuthenticationToken)
                .cast(BearerTokenAuthenticationToken.class)
                .map(BearerTokenAuthenticationToken::getToken)
                .flatMap((accessTokenValue -> {
                    OAuth2AccessToken accessToken = tokenStore.readAccessToken(accessTokenValue);

                    if (accessToken == null) {
                        throw new ServerException(401, "登录状态失效");
                    } else if (accessToken.isExpired()) {
                        throw new ServerException(401, "登录状态失效");
                    } else {
                        OAuth2RefreshToken refreshToken = tokenStore.readRefreshToken(accessToken.getRefreshToken().getValue());
                        if (null == refreshToken) {
                            //注意：不要删除accessToken，否则重复挤下线会有问题
                            throw new ServerException("你已在另一个设备登录");
                        }
                    }

                    OAuth2Authentication result = tokenStore.readAuthentication(accessToken);
                    if (result == null) {
                        throw new ServerException(403, "没有权限");
                    }
                    return Mono.just(result);
                }))
                .cast(Authentication.class);
    }
}
