package com.cms.model.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityRoleTest {

    private SecurityRole securityRole;

    @BeforeEach
    void setUp() {
        securityRole = new SecurityRole();
    }

    @Test
    void getAuthority_returnsRoleValue() {
        securityRole.setRole("ROLE_ADMIN");
        assertEquals("ROLE_ADMIN", securityRole.getAuthority());
    }

    @Test
    void getAuthority_withUserRole() {
        securityRole.setRole("ROLE_USER");
        assertEquals("ROLE_USER", securityRole.getAuthority());
    }

    @Test
    void setAndGetRole() {
        securityRole.setRole("ROLE_MANAGER");
        assertEquals("ROLE_MANAGER", securityRole.getRole());
    }

    @Test
    void setAndGetId() {
        securityRole.setId(5L);
        assertEquals(5L, securityRole.getId());
    }

    @Test
    void role_defaultIsNull() {
        assertNull(securityRole.getRole());
    }

    @Test
    void id_defaultIsNull() {
        assertNull(securityRole.getId());
    }

    @Test
    void allArgsConstructor_setsFieldsCorrectly() {
        SecurityRole role = new SecurityRole(1L, "ROLE_ADMIN");
        assertEquals(1L, role.getId());
        assertEquals("ROLE_ADMIN", role.getRole());
        assertEquals("ROLE_ADMIN", role.getAuthority());
    }
}
