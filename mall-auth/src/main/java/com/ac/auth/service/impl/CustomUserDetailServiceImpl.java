package com.ac.auth.service.impl;

import com.ac.auth.domain.SecurityUser;
import com.ac.auth.enums.SecurityLoginTypeEnum;
import com.ac.auth.enums.SecurityUserTypeEnum;
import com.ac.auth.service.CustomUserDetailsService;
import com.ac.auth.vo.MemberAddSocialVO;
import com.ac.auth.vo.MemberAddVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class CustomUserDetailServiceImpl implements CustomUserDetailsService {

    @Override
    public SecurityUser loadUserByMobile(String mobile) {
        return packageTestUser();
    }

    @Override
    public SecurityUser loadUserByOpenId(String openId, String socialType) {
        return packageTestUser();
    }

    @Override
    public SecurityUser loadUserByIeMi(String ieMi) {
        return packageTestUser();
    }

    @Override
    public SecurityUser loadAdminUser(String mobileOrUserName) {
        return packageTestUser();
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return loadAdminUser(s);
    }

    @Override
    public SecurityUser loadUserById(Long id) {
        return packageTestUser();
    }

    @Override
    public SecurityUser createUserBySocial(MemberAddSocialVO vo) {
        return packageTestUser();
    }

    @Override
    public SecurityUser createUserByMobile(String globalCode, String mobile) {
        return this.createUserByMobile(globalCode, mobile, null);
    }

    @Override
    public SecurityUser createUserByMobile(String globalCode, String mobile, String platform) {
        return packageTestUser();
    }

    @Override
    public SecurityUser createUserByMobile(MemberAddVO vo) {
        return packageTestUser();
    }

    @Override
    public SecurityUser createVisitor(Map<String, String> map) {
        return packageTestUser();
    }

    private SecurityUser packageTestUser() {
        SecurityUser user = SecurityUser.builder()
                .userType(SecurityUserTypeEnum.APP)
                .grantType(SecurityLoginTypeEnum.APP_PWD)
                .id(101L)
                //123abc
                .password("$2a$10$Ugj9R8tLB3QLv531oU91jOb9t9Yx493WroQbksrXYhZCJGSPwNIPK")
                .enabled(true)
                .niceName("AC")
                .build();
        return user;
    }
}
