package com.ac.auth.provider;

import com.ac.oauth2.domain.SecurityUser;
import com.ac.auth.service.CustomUserDetailsService;
import com.ac.oauth2.token.VisitorAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Component
public class VisitorAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        VisitorAuthenticationToken authenticationToken = (VisitorAuthenticationToken) authentication;
        String ieMi = (String) authenticationToken.getPrincipal();
        SecurityUser visitor = customUserDetailsService.loadUserByIeMi(ieMi);
        if (visitor == null) {
            Map<String, String> map = (Map<String, String>) authenticationToken.getDetails();
            visitor = customUserDetailsService.createVisitor(map);
        }

        VisitorAuthenticationToken authenticationResult = new VisitorAuthenticationToken(visitor, visitor.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return VisitorAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
