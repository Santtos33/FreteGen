package com.FreteGen.FreteGen.user;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public enum UserRole {
    ADMIN {
        @Override
        public List<SimpleGrantedAuthority> getAuthorities() {
            return List.of(
                    new SimpleGrantedAuthority("ROLE_ADMIN"),
                    new SimpleGrantedAuthority("ROLE_USER"));
        }
    },

    USER {
        @Override
        public List<SimpleGrantedAuthority> getAuthorities() {
            return List.of(new SimpleGrantedAuthority("ROLE_USER"));
        }
    };

    public abstract List<SimpleGrantedAuthority> getAuthorities();
    }

