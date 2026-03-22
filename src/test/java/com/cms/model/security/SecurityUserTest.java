package com.cms.model.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class SecurityUserTest {

    private SecurityUser securityUser;

    @BeforeEach
    void setUp() {
        securityUser = new SecurityUser();
    }

    @Test
    void setAndGetId() {
        securityUser.setId(1L);
        assertEquals(1L, securityUser.getId());
    }

    @Test
    void setAndGetUsername() {
        securityUser.setUsername("admin");
        assertEquals("admin", securityUser.getUsername());
    }

    @Test
    void setAndGetPassword() {
        securityUser.setPassword("hashed_password");
        assertEquals("hashed_password", securityUser.getPassword());
    }

    @Test
    void setAndGetSecurityRoles() {
        Set<SecurityRole> roles = new HashSet<>();
        SecurityRole role = new SecurityRole(1L, "ROLE_ADMIN");
        roles.add(role);
        securityUser.setSecurityRoles(roles);
        assertNotNull(securityUser.getSecurityRoles());
        assertEquals(1, securityUser.getSecurityRoles().size());
    }

    @Test
    void username_defaultIsNull() {
        assertNull(securityUser.getUsername());
    }

    @Test
    void password_defaultIsNull() {
        assertNull(securityUser.getPassword());
    }

    @Test
    void securityRoles_defaultIsNull() {
        assertNull(securityUser.getSecurityRoles());
    }
}
