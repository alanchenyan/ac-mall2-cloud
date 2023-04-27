package com.ac.auth.provider;

import com.ac.oauth2.domain.SecurityUser;
import com.ac.auth.service.CustomUserDetailsService;
import com.ac.oauth2.token.QRCodeAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Slf4j
@Component
public class QRCodeAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        QRCodeAuthenticationToken authenticationToken = (QRCodeAuthenticationToken) authentication;
        String id = (String) authenticationToken.getPrincipal();

        SecurityUser user = customUserDetailsService.loadUserById(Long.parseLong(id));
        QRCodeAuthenticationToken authenticationResult = new QRCodeAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return QRCodeAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
