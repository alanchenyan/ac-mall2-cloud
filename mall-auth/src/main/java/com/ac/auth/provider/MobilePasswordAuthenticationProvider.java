package com.ac.auth.provider;

import com.ac.oauth2.domain.SecurityUser;
import com.ac.auth.service.CustomUserDetailsService;
import com.ac.auth.token.MobilePasswordAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Component
public class MobilePasswordAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private CustomUserDetailsService customUserDetailsService;

    @Resource
    private PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) {
        MobilePasswordAuthenticationToken authenticationToken = (MobilePasswordAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        String password = (String) authenticationToken.getCredentials();
        SecurityUser user = customUserDetailsService.loadUserByMobile(mobile);

        if (user == null) {
            throw new RuntimeException("用户名错误");
        }

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("密码错误");
        }
        Map<String, String> parameters = (Map<String, String>) authenticationToken.getDetails();
        if (null != parameters.get("platform")) {
            user.setPlatform(parameters.get("platform"));
        }
        MobilePasswordAuthenticationToken authenticationResult = new MobilePasswordAuthenticationToken(user, password, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobilePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
