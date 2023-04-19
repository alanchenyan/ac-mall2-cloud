package com.ac.auth.domain;

import com.ac.auth.enums.SecurityLoginTypeEnum;
import com.ac.auth.enums.SecurityUserTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SecurityUser implements UserDetails {

    /**
     * 登录用户类型
     */
    private SecurityUserTypeEnum userType;

    /**
     * 登录用户登录方式类型
     */
    private SecurityLoginTypeEnum grantType;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户状态
     */
    private boolean enabled;

    /**
     * 权限数据
     */
    private Collection<SimpleGrantedAuthority> authorities;

    private boolean isVisitor;

    private String niceName;

    private String iconUrl;

    private String platform;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
