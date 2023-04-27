package com.ac.auth.controller;

import com.ac.auth.component.AuthRedisHelper;
import com.ac.auth.component.AuthTokenComponent;
import com.ac.auth.dto.Oauth2TokenDTO;
import com.ac.oauth2.enums.SecurityLoginTypeEnum;
import com.ac.auth.util.IpUtil;
import com.ac.auth.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jodd.util.StringUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Api(tags = "会员授权")
@RestController
@RequestMapping("oauth")
public class AuthController {

    @Resource
    private AuthTokenComponent authTokenComponent;

    @Resource
    private AuthRedisHelper authRedisHelper;

    @Resource
    private RedissonClient redissonClient;

    @SneakyThrows
    @PostMapping("pwdLogin")
    @ApiOperation(value = "密码登录")
    public Oauth2TokenDTO pwdLogin(@RequestBody MemberLoginPwdVO vo, HttpServletRequest request) {
        if (StringUtil.isEmpty(vo.getClientId())) {
            vo.setClientId("app");
        }
        vo.setIp(IpUtil.ip(request));
        Map<String, String> params = getMemberBaseParam(vo, SecurityLoginTypeEnum.APP_PWD.getCode());
        params.put("mobile", vo.getMobile());
        params.put("password", vo.getPassword());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Oauth2TokenDTO oauth2Token = authTokenComponent.getAccessToken(vo.getClientId(), "app", grantedAuthorities, params);
        return oauth2Token;
    }

    @SneakyThrows
    @PostMapping("msgCodeLogin")
    @ApiOperation(value = "短信验证码登录")
    public Oauth2TokenDTO msgCodeLogin(@RequestBody MemberLoginMsgCodeVO vo, HttpServletRequest request) {
        log.info("msgCodeLogin:mobile={}", vo.getMobile());
        RLock rdsLock = redissonClient.getLock(vo.getMobile());
        try {
            rdsLock.lock(5, TimeUnit.SECONDS);
            Boolean delRecord = authRedisHelper.getDelRecord(vo.getMobile());
            if (delRecord) {
                throw new RuntimeException("用户已注销");
            }

            vo.setIp(IpUtil.ip(request));
            Map<String, String> params = getMemberBaseParam(vo, SecurityLoginTypeEnum.APP_SMS.getCode());
            params.put("globalCode", vo.getGlobalCode());
            params.put("mobile", vo.getMobile());
            params.put("code", vo.getMsgCode());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            Oauth2TokenDTO oauth2Token = authTokenComponent.getAccessToken(vo.getClientId(), "app", grantedAuthorities, params);
            return oauth2Token;
        } finally {
            // 释放锁
            if (rdsLock.isLocked()) {
                rdsLock.unlock();
            }
        }
    }

    @SneakyThrows
    @PostMapping("oneKeyLogin")
    @ApiOperation(value = "一键登录")
    public Oauth2TokenDTO oneKeyLogin(@RequestBody MemberLoginOneKeyVO vo, HttpServletRequest request) {
        vo.setIp(IpUtil.ip(request));
        Map<String, String> params = getMemberBaseParam(vo, SecurityLoginTypeEnum.APP_ONE_KEY.getCode());
        params.put("token", vo.getToken());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Oauth2TokenDTO oauth2Token = authTokenComponent.getAccessToken(vo.getClientId(), "app", grantedAuthorities, params);
        return oauth2Token;
    }

    @SneakyThrows
    @PostMapping("socialLogin")
    @ApiOperation(value = "第三方登录")
    public Oauth2TokenDTO socialLogin(@RequestBody MemberLoginSocialVO vo, HttpServletRequest request) {
        RLock rdsLock = redissonClient.getLock(vo.getAcc());
        try {
            rdsLock.lock(5, TimeUnit.SECONDS);
            if (StringUtil.isEmpty(vo.getClientId())) {
                vo.setClientId("app");
            }
            vo.setIp(IpUtil.ip(request));
            Map<String, String> params = getMemberBaseParam(vo, SecurityLoginTypeEnum.APP_SOCIAL.getCode());
            params.put("platform", vo.getPlatform());
            params.put("socialType", vo.getSocialType().getCode());
            params.put("acc", vo.getAcc());
            params.put("uid", vo.getUid());
            params.put("iconUrl", vo.getIconUrl());
            params.put("nickName", vo.getNickName());

            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            Oauth2TokenDTO oauth2Token = authTokenComponent.getAccessToken(vo.getClientId(), "app", grantedAuthorities, params);
            return oauth2Token;
        } finally {
            // 释放锁
            if (rdsLock.isLocked()) {
                rdsLock.unlock();
            }
        }
    }

    @SneakyThrows
    @PostMapping("visitor")
    @ApiOperation(value = "游客登录")
    public Oauth2TokenDTO visitorLogin(@RequestBody MemberLoginVisitorVO vo, HttpServletRequest request) {
        vo.setIp(IpUtil.ip(request));
        Map<String, String> params = getMemberBaseParam(vo, SecurityLoginTypeEnum.APP_ANONYMOUS.getCode());
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        Oauth2TokenDTO oauth2Token = authTokenComponent.getAccessToken(vo.getClientId(), "app", grantedAuthorities, params);
        return oauth2Token;
    }

    private Map<String, String> getMemberBaseParam(MemberLoginBaseVO vo, String grantType) {
        Map<String, String> params = new HashMap<>();
        params.put("client_id", vo.getClientId());
        params.put("client_secret", "app");
        params.put("grant_type", grantType);
        params.put("scope", "all");
        params.put("platform", vo.getPlatform());
        //附加信息
        params.put("version", vo.getVersion());
        params.put("device", vo.getDevice());
        params.put("iemi", vo.getIemi());
        params.put("location", vo.getLocation());
        params.put("ip", vo.getIp());
        params.put("recommendCode", vo.getRecommendCode());
        return params;
    }
}
