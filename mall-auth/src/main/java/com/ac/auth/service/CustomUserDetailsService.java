package com.ac.auth.service;

import com.ac.auth.domain.SecurityUser;
import com.ac.auth.vo.MemberAddSocialVO;
import com.ac.auth.vo.MemberAddVO;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

public interface CustomUserDetailsService extends UserDetailsService {

    SecurityUser loadUserByMobile(String mobile);

    SecurityUser loadUserById(Long id);

    SecurityUser loadUserByOpenId(String openId, String socialType);

    SecurityUser loadUserByIeMi(String ieMi);

    SecurityUser loadAdminUser(String mobileOrUserName);

    SecurityUser createUserBySocial(MemberAddSocialVO vo);

    SecurityUser createUserByMobile(String globalCode, String mobile);

    SecurityUser createUserByMobile(String globalCode, String mobile, String platform);

    SecurityUser createUserByMobile(MemberAddVO vo);

    SecurityUser createVisitor(Map<String, String> map);
}
