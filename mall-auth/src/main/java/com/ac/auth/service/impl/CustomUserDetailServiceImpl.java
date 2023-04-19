package com.ac.auth.service.impl;

import com.ac.auth.domain.SecurityUser;
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
        return null;
    }

    @Override
    public SecurityUser loadUserByOpenId(String openId, String socialType) {
        return null;
    }

    @Override
    public SecurityUser loadUserByIeMi(String ieMi) {
        return null;
    }

    @Override
    public SecurityUser loadAdminUser(String mobileOrUserName) {
        return null;
    }

    @Override
    public SecurityUser createUserBySocial(MemberAddSocialVO vo) {
        return null;
    }

    @Override
    public SecurityUser createUserByMobile(String globalCode, String mobile) {
        return this.createUserByMobile(globalCode, mobile, null);
    }

    @Override
    public SecurityUser createUserByMobile(String globalCode, String mobile, String platform) {
        return null;
    }

    @Override
    public SecurityUser createUserByMobile(MemberAddVO vo) {
        return null;
    }

    @Override
    public SecurityUser createVisitor(Map<String, String> map) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return loadAdminUser(s);
    }

    @Override
    public SecurityUser loadUserById(Long id) {
        return null;
    }
}
