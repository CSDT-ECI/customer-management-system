package com.cms.controller;

import com.cms.model.security.SecurityRole;
import com.cms.model.security.SecurityUser;
import com.cms.service.security.impl.SecurityUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MainRestControllerTest {

    @Mock
    private SecurityUserService userService;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new MainRestController(userService))
            .setMessageConverters(new MappingJackson2HttpMessageConverter())
            .build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void register_returnsCreatedUserPayload() throws Exception {
        SecurityUser savedUser = new SecurityUser();
        savedUser.setId(1L);
        savedUser.setUsername("admin");
        savedUser.setPassword("encoded-password");
        savedUser.setSecurityRoles(Collections.singleton(new SecurityRole(1L, "ROLE_ADMIN")));

        when(userService.register(any(SecurityUser.class))).thenReturn(savedUser);

        SecurityUser request = new SecurityUser();
        request.setUsername("admin");
        request.setPassword("123456");

        mockMvc.perform(post("/api/user/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.username").value("admin"))
            .andExpect(jsonPath("$.securityRoles[0].role").value("ROLE_ADMIN"));

        verify(userService).register(any(SecurityUser.class));
    }
}