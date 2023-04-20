package com.ac.auth.config;

import com.ac.auth.domain.SecurityUser;
import com.ac.auth.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.UnsupportedResponseTypeException;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.JdbcClientDetailsService;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alan Chen
 * @description 认证配置
 * @date 2023/02/22
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

    @Resource
    private AuthenticationManager authenticationManager;

    @Resource
    private CustomUserDetailsService customUserDetailsServiceImpl;

    @Resource
    private WebResponseExceptionTranslator webResponseExceptionTranslator;

    @Resource
    private RandomValueAuthorizationCodeServices redisAuthorizationCodeServiceImpl;

    @Resource
    private JdbcClientDetailsService customJdbcClientDetailsServiceImpl;

    @Resource
    private TokenStore tokenStore;

    @Resource
    private TokenGranter tokenGranter;

    @Bean
    public WebResponseExceptionTranslator webResponseExceptionTranslator() {
        return new DefaultWebResponseExceptionTranslator() {
            private static final String BAD_MSG = "坏的凭证";
            private static final String BAD_MSG_EN = "Bad credentials";

            @Override
            public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
                OAuth2Exception oAuth2Exception;
                if (e.getMessage() != null
                        && (BAD_MSG.equals(e.getMessage()) || BAD_MSG_EN.equals(e.getMessage()))) {
                    oAuth2Exception = new InvalidGrantException("用户名或密码错误", e);
                } else if (e instanceof InternalAuthenticationServiceException) {
                    oAuth2Exception = new InvalidGrantException(e.getMessage(), e);
                } else if (e instanceof OAuth2Exception) {
                    oAuth2Exception = (OAuth2Exception) e;
                } else {
                    oAuth2Exception = new UnsupportedResponseTypeException("服务内部错误", e);
                }
                ResponseEntity<OAuth2Exception> response = super.translate(oAuth2Exception);
                ResponseEntity.status(oAuth2Exception.getHttpErrorCode());
                response.getBody().addAdditionalInformation("resp_code", oAuth2Exception.getHttpErrorCode() + "");
                response.getBody().addAdditionalInformation("resp_msg", oAuth2Exception.getMessage());

                return response;
            }
        };
    }

    /**
     * 用来配置令牌的访问端点和令牌服务
     *
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        // 配置token存储，一般配置redis存储
        endpoints.tokenStore(tokenStore)
                //配置认证管理器
                .authenticationManager(authenticationManager)

                //配置用户详情server，密码模式必须
                .userDetailsService(customUserDetailsServiceImpl)

                //配置授权码模式授权码服务,不配置默认为内存模式
                .authorizationCodeServices(redisAuthorizationCodeServiceImpl)

                //配置响应异常转换器
                .exceptionTranslator(webResponseExceptionTranslator)

                // 配置grant_type模式，如果不配置则默认使用密码模式、简化模式、验证码模式以及刷新token模式，如果配置了只使用配置中，默认配置失效
                // 具体可以查询AuthorizationServerEndpointsConfigurer中的getDefaultTokenGranters方法
                .tokenGranter(tokenGranter);
    }

    /**
     * 配置客户端详情（根据客户的id查询客户端）
     *
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //设置客户端信息，可以将客户端信息放在内存、数据库等。前端请求时将做校验
        clients.withClientDetails(customJdbcClientDetailsServiceImpl);
    }

    /**
     * 用来配置令牌端点的安全约束
     *
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        //tokenKeyAccess("permitAll()") /oauth/token_key 公开, 默认拒绝访问
        //checkTokenAccess("isAuthenticated()")  认证后可访问 /oauth/check_token , 默认拒绝访问
        security
                .tokenKeyAccess("isAuthenticated()")
                .checkTokenAccess("permitAll()")
                .allowFormAuthenticationForClients();
    }

    /**
     * token增强,添加一些元信息
     *
     * @return
     */
    @Bean
    public TokenEnhancer tokenEnhancer() {
        return (accessToken, authentication) -> {
            Map<String, Object> additionalInfo = new HashMap<>(2);
            Object principal = authentication.getPrincipal();
            //增加id参数
            if (principal instanceof SecurityUser) {
                SecurityUser user = (SecurityUser) principal;
                additionalInfo.put("userType", user.getUserType().name());
                additionalInfo.put("grantType", user.getGrantType().getCode());
                additionalInfo.put("id", user.getId());
                additionalInfo.put("userName", user.getUsername());
                additionalInfo.put("enabled", user.isEnabled());
                additionalInfo.put("platform", user.getPlatform());
            }
            ((DefaultOAuth2AccessToken) accessToken).setAdditionalInformation(additionalInfo);
            return accessToken;
        };
    }
}
