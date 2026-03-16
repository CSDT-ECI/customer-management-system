package com.cms.contextHolder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginComponentTest {

    private LoginComponent loginComponent;

    @BeforeEach
    void setUp() {
        loginComponent = new LoginComponent();
    }

    @Test
    void defaultLogin_isEmptyString() {
        assertEquals("", loginComponent.getLogin());
    }

    @Test
    void defaultPassword_isEmptyString() {
        assertEquals("", loginComponent.getPassword());
    }

    @Test
    void tryToLogin_withAdminUser_returnsTrue() {
        loginComponent.setLogin("admin");
        assertTrue(loginComponent.tryToLogin());
    }

    @Test
    void tryToLogin_withNonAdminUser_returnsFalse() {
        loginComponent.setLogin("user");
        assertFalse(loginComponent.tryToLogin());
    }

    @Test
    void tryToLogin_withEmptyLogin_returnsFalse() {
        loginComponent.setLogin("");
        assertFalse(loginComponent.tryToLogin());
    }

    @Test
    void setAndGetLogin() {
        loginComponent.setLogin("testUser");
        assertEquals("testUser", loginComponent.getLogin());
    }

    @Test
    void setAndGetPassword() {
        loginComponent.setPassword("secret");
        assertEquals("secret", loginComponent.getPassword());
    }

    @Test
    void principal_defaultIsNull() {
        assertNull(loginComponent.getPrincipal());
    }
}
