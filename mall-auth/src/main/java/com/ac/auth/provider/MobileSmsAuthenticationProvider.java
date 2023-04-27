package com.ac.auth.provider;

import com.ac.auth.component.SmsCodeRdsComponent;
import com.ac.oauth2.domain.SecurityUser;
import com.ac.oauth2.enums.PlatformEnum;
import com.ac.oauth2.enums.SmsBuzTypeEnum;
import com.ac.auth.service.CustomUserDetailsService;
import com.ac.oauth2.token.MobileSmsAuthenticationToken;
import com.ac.auth.vo.MemberAddVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;

@Slf4j
@Component
public class MobileSmsAuthenticationProvider implements AuthenticationProvider {
    @Resource
    private CustomUserDetailsService customUserDetailsService;

    @Resource
    private SmsCodeRdsComponent smsCodeRdsComponent;

    @Override
    public Authentication authenticate(Authentication authentication) {
        MobileSmsAuthenticationToken authenticationToken = (MobileSmsAuthenticationToken) authentication;
        Map<String, String> parameters = (Map<String, String>) authenticationToken.getDetails();
        String mobile = (String) authenticationToken.getPrincipal();
        String msgCode = (String) authenticationToken.getCredentials();

        String globalCode = parameters.get("globalCode");
        String platform = parameters.get("platform");

        if (!smsCodeRdsComponent.codeVerify(SmsBuzTypeEnum.LOGIN_CODE, mobile, msgCode)) {
            throw new RuntimeException("短信验证码错误");
        }

        SecurityUser user = customUserDetailsService.loadUserByMobile(mobile);
        if (user == null) {
            MemberAddVO memberAddVO = new MemberAddVO();
            memberAddVO.setGlobalCode(globalCode == null ? "86" : globalCode);
            memberAddVO.setMobile(mobile);
            memberAddVO.setRegisterDevice(PlatformEnum.parse(platform));
            memberAddVO.setIp(parameters.get("ip"));
            //自动注册一个用户
            user = customUserDetailsService.createUserByMobile(memberAddVO);
        }

        if (null != parameters.get("platform")) {
            user.setPlatform(parameters.get("platform"));
        }
        MobileSmsAuthenticationToken authenticationResult = new MobileSmsAuthenticationToken(user, msgCode, user.getAuthorities());
        authenticationResult.setDetails(authenticationToken.getDetails());
        return authenticationResult;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return MobileSmsAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
