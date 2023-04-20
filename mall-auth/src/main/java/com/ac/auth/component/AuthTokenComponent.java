package com.ac.auth.component;

import com.ac.auth.dto.Oauth2TokenDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class AuthTokenComponent {

    @Resource
    private TokenEndpoint tokenEndpoint;

    public Oauth2TokenDTO getAccessToken(String clientId, String clientSecurity, List<GrantedAuthority> grantedAuthorities, Map<String, String> params) throws HttpRequestMethodNotSupportedException {
        User principle = new User(clientId, clientSecurity, true, true, true, true, grantedAuthorities);
        return getAccessToken(principle, params);
    }

    public Oauth2TokenDTO getAccessToken(User principle, Map<String, String> params) throws HttpRequestMethodNotSupportedException {
        UsernamePasswordAuthenticationToken principal = new UsernamePasswordAuthenticationToken(principle, null, principle.getAuthorities());
        OAuth2AccessToken oAuth2AccessToken = tokenEndpoint.postAccessToken(principal, params).getBody();
        Oauth2TokenDTO oauth2TokenDto = Oauth2TokenDTO.builder()
                .token(oAuth2AccessToken.getValue())
                .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                .expiresIn(oAuth2AccessToken.getExpiresIn())
                .tokenHead("Bearer ").build();
        return oauth2TokenDto;
    }
}
