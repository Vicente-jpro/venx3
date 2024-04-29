package com.adicionatec.venx3.utils;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.adicionatec.venx3.models.Usuario;

import lombok.Data;

@Data
public class CurrentUser implements UserDetails {

    private Usuario usuario;
    private UserDetails userDetails;

    public CurrentUser(UserDetails userDetails, Usuario usuario) {
        this.userDetails = userDetails;
        this.usuario = usuario;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return AuthorityUtils.createAuthorityList("" + userDetails.getAuthorities());
    }

    @Override
    public String getPassword() {
        return this.userDetails.getPassword();
    }

    @Override
    public String getUsername() {
        return this.userDetails.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.userDetails.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.userDetails.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.userDetails.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return this.userDetails.isEnabled();
    }

}
