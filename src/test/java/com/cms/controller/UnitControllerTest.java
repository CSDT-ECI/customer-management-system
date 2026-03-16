package com.cms.controller;

import com.cms.model.Unit;
import com.cms.service.AbstractService;
import com.cms.service.UnitService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UnitControllerTest {

    @Mock
    private AbstractService<Unit> service;

    @Mock
    private UnitService unitService;

    private UnitController controller;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new UnitController();
        controller.service = service;
        ReflectionTestUtils.setField(controller, "unitService", unitService);
        objectMapper = new ObjectMapper();

        when(service.findAll()).thenReturn(Collections.emptyList());
        controller.init();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter())
            .build();
    }

    @Test
    void findAll_returnsHttpOkAndUnitList() throws Exception {
        when(service.findAll()).thenReturn(Arrays.asList(unit(1L, "Proveedor A"), unit(2L, "Cliente B")));

        mockMvc.perform(get("/unit"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Proveedor A"))
            .andExpect(jsonPath("$[1].name").value("Cliente B"));
    }

    @Test
    void createUnit_returnsSavedEntity() throws Exception {
        when(service.save(any(Unit.class))).thenAnswer(invocation -> {
            Unit unit = invocation.getArgument(0);
            unit.setId(15L);
            return unit;
        });

        mockMvc.perform(post("/unit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unit(0L, "Unidad Nueva"))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(15))
            .andExpect(jsonPath("$.name").value("Unidad Nueva"));

        verify(service).save(any(Unit.class));
    }

    @Test
    void updateUnit_returnsUpdatedEntity() throws Exception {
        when(service.save(any(Unit.class))).thenAnswer(invocation -> invocation.getArgument(0));

        mockMvc.perform(put("/unit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(unit(9L, "Unidad Actualizada"))))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(9))
            .andExpect(jsonPath("$.name").value("Unidad Actualizada"));
    }

    @Test
    void deleteUnit_returnsDeletedEntity() throws Exception {
        when(service.findById(3L)).thenReturn(unit(3L, "Unidad Eliminada"));

        mockMvc.perform(delete("/unit/3"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(3))
            .andExpect(jsonPath("$.name").value("Unidad Eliminada"));

        verify(service).findById(3L);
        verify(service).delete(3L);
    }

    @Test
    void getProviderList_delegatesToUnitService() {
        List<Unit> providers = Collections.singletonList(unit(1L, "Proveedor Principal"));
        when(unitService.getProviderList()).thenReturn(providers);

        List<Unit> result = controller.getProviderList();

        assertEquals(1, result.size());
        assertEquals("Proveedor Principal", result.get(0).getName());
        verify(unitService).getProviderList();
    }

    @Test
    void getClientList_delegatesToUnitService() {
        List<Unit> clients = Collections.singletonList(unit(2L, "Cliente Principal"));
        when(unitService.getClientList()).thenReturn(clients);

        List<Unit> result = controller.getClientList();

        assertEquals(1, result.size());
        assertEquals("Cliente Principal", result.get(0).getName());
        verify(unitService).getClientList();
    }

    @Test
    void addPerson_addsNewPersonToSelectedObject() {
        controller.getSelectedObject().getPersons().clear();

        controller.addPerson();

        assertEquals(1, controller.getSelectedObject().getPersons().size());
    }

    private Unit unit(long id, String name) {
        Unit unit = new Unit();
        unit.setId(id);
        unit.setName(name);
        return unit;
    }
}