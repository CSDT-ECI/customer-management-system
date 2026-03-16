package com.cms.controller;

import com.cms.model.Country;
import com.cms.service.AbstractService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CountryControllerTest {

    @Mock
    private AbstractService<Country> service;

    private CountryController controller;
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        controller = new CountryController();
        controller.service = service;
        objectMapper = new ObjectMapper();

        when(service.findAll()).thenReturn(Collections.emptyList());
        controller.init();

        mockMvc = MockMvcBuilders.standaloneSetup(controller)
            .setMessageConverters(new MappingJackson2HttpMessageConverter())
            .build();
    }

    @Test
    void init_populatesListAndSelectedObject() {
        Country country = country(1L, "Colombia");
        when(service.findAll()).thenReturn(Collections.singletonList(country));

        controller.init();

        assertEquals(1, controller.getList().size());
        assertNotNull(controller.getSelectedObject());
        assertEquals("Country", controller.getSelectedObject().getClass().getSimpleName());
    }

    @Test
    void findAll_returnsHttpOkAndCountryList() throws Exception {
        when(service.findAll()).thenReturn(Arrays.asList(country(1L, "Colombia"), country(2L, "Peru")));

        mockMvc.perform(get("/country"))
            .andExpect(status().isOk())
            .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].name").value("Colombia"))
            .andExpect(jsonPath("$[1].name").value("Peru"));
    }

    @Test
    void findById_returnsHttpOkAndCountry() throws Exception {
        when(service.findById(7L)).thenReturn(country(7L, "Ecuador"));

        mockMvc.perform(get("/country/7"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(7))
            .andExpect(jsonPath("$.name").value("Ecuador"));
    }

    @Test
    void createCountry_returnsSavedEntity() throws Exception {
        when(service.save(any(Country.class))).thenAnswer(invocation -> {
            Country country = invocation.getArgument(0);
            country.setId(10L);
            return country;
        });

        Country request = country(0L, "Chile");

        mockMvc.perform(post("/country")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(10))
            .andExpect(jsonPath("$.name").value("Chile"));

        verify(service).save(any(Country.class));
    }

    @Test
    void updateCountry_returnsUpdatedEntity() throws Exception {
        when(service.save(any(Country.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Country request = country(11L, "Argentina");

        mockMvc.perform(put("/country")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(11))
            .andExpect(jsonPath("$.name").value("Argentina"));

        verify(service).save(any(Country.class));
    }

    @Test
    void deleteCountry_returnsDeletedEntity() throws Exception {
        when(service.findById(4L)).thenReturn(country(4L, "Bolivia"));

        mockMvc.perform(delete("/country/4"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(4))
            .andExpect(jsonPath("$.name").value("Bolivia"));

        verify(service).findById(4L);
        verify(service).delete(4L);
    }

    @Test
    void saveManagedBean_returnsCountryListView() {
        Country selectedCountry = country(0L, "Uruguay");
        controller.setSelectedObject(selectedCountry);
        when(service.save(any(Country.class))).thenReturn(selectedCountry);
        when(service.findAll()).thenReturn(Collections.singletonList(selectedCountry));

        String view = controller.save();

        assertEquals("countryList", view);
        verify(service, atLeastOnce()).save(any(Country.class));
    }

    @Test
    void removeManagedBean_returnsCountryListView() {
        Country selectedCountry = country(12L, "Paraguay");
        when(service.findById(12L)).thenReturn(selectedCountry);

        String view = controller.remove(selectedCountry);

        assertEquals("countryList", view);
        verify(service).delete(12L);
    }

    @Test
    void edit_setsSelectedObjectAndReturnsView() {
        Country selectedCountry = country(8L, "Brasil");

        String editView = controller.edit(selectedCountry);

        assertEquals("countryView", editView);
        assertEquals(selectedCountry, controller.getSelectedObject());
    }

    @Test
    void cancel_reinitializesControllerAndReturnsListView() {
        controller.setSelectedObject(country(8L, "Brasil"));

        String cancelView = controller.cancel();

        assertEquals("countryList", cancelView);
        assertNotNull(controller.getSelectedObject());
    }

    @Test
    void getEditButtonCaption_changesAccordingToSelectedObjectId() {
        controller.setSelectedObject(country(0L, "Nueva"));
        assertEquals("Save", controller.getEditButtonCaption());

        controller.setSelectedObject(country(5L, "Existente"));
        assertEquals("Update", controller.getEditButtonCaption());
    }

    @Test
    void getRandomObject_returnsOnlyAvailableCountry() {
        Country country = country(2L, "Mexico");
        controller.setList(Collections.singletonList(country));

        assertEquals(country, controller.getRandomObject());
    }

    private Country country(long id, String name) {
        Country country = new Country();
        country.setId(id);
        country.setName(name);
        return country;
    }
}