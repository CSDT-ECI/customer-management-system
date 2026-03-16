package com.cms.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ChartTypeTest {

    private ChartType chartType;

    @BeforeEach
    void setUp() {
        chartType = new ChartType();
    }

    @Test
    void newChartType_createdDateIsSetAutomatically() {
        assertNotNull(chartType.getCreatedDate());
    }

    @Test
    void setAndGetUiType() {
        chartType.setUiType("pie");
        assertEquals("pie", chartType.getUiType());
    }

    @Test
    void setAndGetUiType_bar() {
        chartType.setUiType("bar");
        assertEquals("bar", chartType.getUiType());
    }

    @Test
    void uiType_defaultIsNull() {
        assertNull(chartType.getUiType());
    }

    @Test
    void setAndGetName_fromCoreEntity() {
        chartType.setName("Bar Chart");
        assertEquals("Bar Chart", chartType.getName());
    }

    @Test
    void setAndGetId_fromCoreEntity() {
        chartType.setId(10L);
        assertEquals(10L, chartType.getId());
    }

    @Test
    void setAndGetNotes_fromCoreEntity() {
        chartType.setNotes("Used for comparison");
        assertEquals("Used for comparison", chartType.getNotes());
    }

    @Test
    void toString_returnsName() {
        chartType.setName("Line Chart");
        assertEquals("Line Chart", chartType.toString());
    }

    @Test
    void toString_withNullName_returnsNull() {
        assertNull(chartType.getName());
        assertEquals("null", chartType.toString());
    }
}
