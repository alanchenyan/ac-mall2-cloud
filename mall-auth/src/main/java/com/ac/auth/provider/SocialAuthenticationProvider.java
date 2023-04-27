package com.ac.auth.provider;

import com.ac.oauth2.domain.SecurityUser;
import com.ac.oauth2.enums.MemberSocialTypeEnum;
import com.ac.oauth2.enums.PlatformEnum;
import com.ac.auth.service.CustomUserDetailsService;
import com.ac.oauth2.token.SocialAuthenticationToken;
import com.ac.auth.vo.MemberAddSocialVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Component
public class SocialAuthenticationProvider implements AuthenticationProvider {

    @Resource
    private CustomUserDetailsService customUserDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) {
        SocialAuthenticationToken authenticationToken = (SocialAuthenticationToken) authentication;
        String socialType = (String) authenticationToken.getPrincipal();
        String openId = (String) authenticationToken.getCredentials();

        SecurityUser user = customUserDetailsService.loadUserByOpenId(openId, socialType);
        if (user == null) {
            Map<String, String> parameters = (Map<String, String>) authenticationToken.getDetails();
            //创建新的用户
            MemberAddSocialVO vo = new MemberAddSocialVO();
            vo.setPlatform(PlatformEnum.parse(parameters.get("platform")));
            vo.setNickName(parameters.get("nickName"));
            vo.setIconUrl(parameters.get("iconUrl"));
            vo.setSocialType(MemberSocialTypeEnum.parse(parameters.get("socialType")));
            vo.setUid(parameters.get("uid"));
            vo.setIp(parameters.get("ip"));
            user = customUserDetailsService.createUserBySocial(vo);
        }
        SocialAuthenticationToken authenticationResult = new SocialAuthenticationToken(user, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SocialAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
