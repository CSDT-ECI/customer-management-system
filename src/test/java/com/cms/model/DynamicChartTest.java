package com.cms.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicChartTest {

    private DynamicChart dynamicChart;

    @BeforeEach
    void setUp() {
        dynamicChart = new DynamicChart("graphicObject", "bar");
    }

    @Test
    void constructor_setsGraphic() {
        assertEquals("graphicObject", dynamicChart.getGraphic());
    }

    @Test
    void constructor_setsUiType() {
        assertEquals("bar", dynamicChart.getUiType());
    }

    @Test
    void setAndGetGraphic() {
        Object newGraphic = new Object();
        dynamicChart.setGraphic(newGraphic);
        assertSame(newGraphic, dynamicChart.getGraphic());
    }

    @Test
    void setAndGetUiType() {
        dynamicChart.setUiType("line");
        assertEquals("line", dynamicChart.getUiType());
    }

    @Test
    void setGraphic_toNull() {
        dynamicChart.setGraphic(null);
        assertNull(dynamicChart.getGraphic());
    }

    @Test
    void setUiType_toNull() {
        dynamicChart.setUiType(null);
        assertNull(dynamicChart.getUiType());
    }

    @Test
    void constructor_withNullValues() {
        DynamicChart chart = new DynamicChart(null, null);
        assertNull(chart.getGraphic());
        assertNull(chart.getUiType());
    }
}
