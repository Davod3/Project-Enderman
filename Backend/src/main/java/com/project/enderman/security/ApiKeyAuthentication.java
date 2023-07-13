package com.project.enderman.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

public class ApiKeyAuthentication extends AbstractAuthenticationToken {

    private final String apiKey;

    public ApiKeyAuthentication(String apiKey, Collection<? extends GrantedAuthority> authorities) {

        super(authorities);
        this.apiKey = apiKey;
        setAuthenticated(true);

    }

    @Override
    public Object getCredentials(){
        return null;
    }

    @Override
    public Object getPrincipal() {
        return apiKey;
    }
}