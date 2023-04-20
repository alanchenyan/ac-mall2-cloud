package com.ac.auth.service.impl;

import com.ac.auth.domain.SecurityUser;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.gen.RSAKeyGenerator;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Slf4j
public class CustomTokenServiceImpl extends DefaultTokenServices {
    private TokenStore tokenStore;
    private TokenEnhancer accessTokenEnhancer;

    /**
     * 是否登录同应用同账号互踢
     */
    private boolean isSingleLogin;

    public CustomTokenServiceImpl(boolean isSingleLogin) {
        this.isSingleLogin = isSingleLogin;
    }

    @Override
    @Transactional
    public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
        OAuth2AccessToken existingAccessToken = tokenStore.getAccessToken(authentication);
        OAuth2RefreshToken refreshToken = null;

        if (existingAccessToken != null) {
            if (isSingleLogin) {
                if (existingAccessToken.getRefreshToken() != null) {
                    tokenStore.removeRefreshToken(existingAccessToken.getRefreshToken());
                }
            } else if (existingAccessToken.isExpired()) {
                if (existingAccessToken.getRefreshToken() != null) {
                    refreshToken = existingAccessToken.getRefreshToken();
                    // The token store could remove the refresh token when the
                    // access token is removed, but we want to
                    // be sure...
                    tokenStore.removeRefreshToken(refreshToken);
                }
                tokenStore.removeAccessToken(existingAccessToken);
                log.info("createAccessToken,isExpired B={}",existingAccessToken);
            } else {
                // oidc每次授权都刷新id_token
                existingAccessToken = refreshIdToken(existingAccessToken, authentication);
                // Re-store the access token in case the authentication has changed
                tokenStore.storeAccessToken(existingAccessToken, authentication);
                log.info("createAccessToken,isExpired C={}",existingAccessToken);
                return existingAccessToken;
            }
        }

        // Only create a new refresh token if there wasn't an existing one
        // associated with an expired access token.
        // Clients might be holding existing refresh tokens, so we re-use it in
        // the case that the old access token
        // expired.
        if (refreshToken == null) {
            refreshToken = createRefreshToken(authentication);
        }
        // But the refresh token itself might need to be re-issued if it has
        // expired.
        else if (refreshToken instanceof ExpiringOAuth2RefreshToken) {
            ExpiringOAuth2RefreshToken expiring = (ExpiringOAuth2RefreshToken) refreshToken;
            if (System.currentTimeMillis() > expiring.getExpiration().getTime()) {
                refreshToken = createRefreshToken(authentication);
            }
        }

        OAuth2AccessToken accessToken = createAccessToken(authentication, refreshToken);
        tokenStore.storeAccessToken(accessToken, authentication);
        // In case it was modified
        refreshToken = accessToken.getRefreshToken();
        if (refreshToken != null) {
            tokenStore.storeRefreshToken(refreshToken, authentication);
        }
        log.info("createAccessToken,end accessToken={}",accessToken);
        return accessToken;
    }

    /**
     * oidc每次授权都刷新id_token
     *
     * @param token          已存在的token
     * @param authentication 认证信息
     */
    private OAuth2AccessToken refreshIdToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
        Set<String> responseTypes = authentication.getOAuth2Request().getResponseTypes();
        if (accessTokenEnhancer != null && responseTypes.contains("id_token")) {
            return accessTokenEnhancer.enhance(token, authentication);
        }
        return token;
    }

    private OAuth2RefreshToken createRefreshToken(OAuth2Authentication authentication) {
        if (!isSupportRefreshToken(authentication.getOAuth2Request())) {
            return null;
        }
        int validitySeconds = getRefreshTokenValiditySeconds(authentication.getOAuth2Request());
        String value = createToken(authentication);
        if (validitySeconds > 0) {
            return new DefaultExpiringOAuth2RefreshToken(value, new Date(System.currentTimeMillis()
                    + (validitySeconds * 1000L)));
        }
        return new DefaultOAuth2RefreshToken(value);
    }

    private OAuth2AccessToken createAccessToken(OAuth2Authentication authentication, OAuth2RefreshToken refreshToken) {
        DefaultOAuth2AccessToken token = new DefaultOAuth2AccessToken(createToken(authentication));
        int validitySeconds = getAccessTokenValiditySeconds(authentication.getOAuth2Request());
        if (validitySeconds > 0) {
            token.setExpiration(new Date(System.currentTimeMillis() + (validitySeconds * 1000L)));
        }
        token.setRefreshToken(refreshToken);
        token.setScope(authentication.getOAuth2Request().getScope());

        return accessTokenEnhancer != null ? accessTokenEnhancer.enhance(token, authentication) : token;
    }

    @Override
    public void setTokenStore(TokenStore tokenStore) {
        this.tokenStore = tokenStore;
        super.setTokenStore(tokenStore);
    }

    @Override
    public void setTokenEnhancer(TokenEnhancer accessTokenEnhancer) {
        this.accessTokenEnhancer = accessTokenEnhancer;
        super.setTokenEnhancer(accessTokenEnhancer);
    }

    private String randomUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    private String createToken(OAuth2Authentication authentication) {
        Object obj = authentication.getPrincipal();
        if (obj != null && obj instanceof SecurityUser) {
            SecurityUser user = (SecurityUser) obj;
            try {

                //生成秘钥,秘钥是token的签名方持有
                RSASSASigner rsassaSigner = new RSASSASigner(getKey());
                long validitySeconds = 2592000L;

                //建立payload 载体
                JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                        .expirationTime(new Date(System.currentTimeMillis() + (validitySeconds * 1000L)))
                        .claim("id", user.getId())
                        .claim("userName", user.getUsername())
                        .build();

                //建立签名
                SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.RS256), claimsSet);
                signedJWT.sign(rsassaSigner);

                //生成token
                String token = signedJWT.serialize();
                return token;

            } catch (JOSEException e) {
                e.printStackTrace();
                log.error("生成token失败：{}",e);
            }
        }
        return null;
    }

    /**
     * 创建加密key
     */
    private RSAKey getKey() {
        try {
            RSAKeyGenerator rsaKeyGenerator = new RSAKeyGenerator(2048);
            RSAKey rsaJWK = rsaKeyGenerator.generate();
            return rsaJWK;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
