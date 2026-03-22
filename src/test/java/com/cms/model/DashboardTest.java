package com.cms.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DashboardTest {

    private Dashboard dashboard;

    @BeforeEach
    void setUp() {
        dashboard = new Dashboard();
    }

    @Test
    void newDashboard_createdDateIsSetAutomatically() {
        assertNotNull(dashboard.getCreatedDate());
    }

    @Test
    void setAndGetQuery() {
        dashboard.setQuery("SELECT * FROM unit");
        assertEquals("SELECT * FROM unit", dashboard.getQuery());
    }

    @Test
    void setAndGetYmax() {
        dashboard.setYmax(100L);
        assertEquals(100L, dashboard.getYmax());
    }

    @Test
    void setAndGetYmin() {
        dashboard.setYmin(0L);
        assertEquals(0L, dashboard.getYmin());
    }

    @Test
    void setAndGetLegendPosition() {
        dashboard.setLegendPosition("bottom");
        assertEquals("bottom", dashboard.getLegendPosition());
    }

    @Test
    void setAndGetShowColumn() {
        dashboard.setShowColumn("name");
        assertEquals("name", dashboard.getShowColumn());
    }

    @Test
    void setAndGetSeriesTags() {
        dashboard.setSeriesTags("sales,revenue");
        assertEquals("sales,revenue", dashboard.getSeriesTags());
    }

    @Test
    void getSeriesList_withNullSeriesTags_returnsEmptyList() {
        dashboard.setSeriesTags(null);
        List<String> result = dashboard.getSeriesList();
        assertNotNull(result);
        assertTrue(result.isEmpty(), "Should return empty list when seriesTags is null");
    }

    @Test
    void getSeriesList_withSingleTag_returnsListWithOneElement() {
        dashboard.setSeriesTags("sales");
        List<String> result = dashboard.getSeriesList();
        assertEquals(1, result.size());
        assertEquals("sales", result.get(0));
    }

    @Test
    void getSeriesList_withMultipleTags_returnsCorrectList() {
        dashboard.setSeriesTags("sales,revenue,profit");
        List<String> result = dashboard.getSeriesList();
        assertEquals(3, result.size());
        assertEquals("sales", result.get(0));
        assertEquals("revenue", result.get(1));
        assertEquals("profit", result.get(2));
    }

    @Test
    void setSeriesList_joinsWithComma() {
        List<String> series = Arrays.asList("alpha", "beta", "gamma");
        dashboard.setSeriesList(series);
        assertEquals("alpha,beta,gamma", dashboard.getSeriesTags());
    }

    @Test
    void setSeriesList_emptyList_setsEmptyString() {
        dashboard.setSeriesList(Collections.emptyList());
        assertEquals("", dashboard.getSeriesTags());
    }

    @Test
    void setAndGetChartType() {
        ChartType chartType = new ChartType();
        chartType.setUiType("bar");
        dashboard.setChartType(chartType);
        assertNotNull(dashboard.getChartType());
        assertEquals("bar", dashboard.getChartType().getUiType());
    }

    @Test
    void setAndGetShow() {
        Constant show = new Constant();
        show.setName("true");
        dashboard.setShow(show);
        assertNotNull(dashboard.getShow());
        assertEquals("true", dashboard.getShow().getName());
    }

    @Test
    void setAndGetAnimate() {
        Constant animate = new Constant();
        animate.setName("false");
        dashboard.setAnimate(animate);
        assertNotNull(dashboard.getAnimate());
        assertEquals("false", dashboard.getAnimate().getName());
    }

    @Test
    void setAndGetName_fromCoreEntity() {
        dashboard.setName("My Dashboard");
        assertEquals("My Dashboard", dashboard.getName());
    }
}
