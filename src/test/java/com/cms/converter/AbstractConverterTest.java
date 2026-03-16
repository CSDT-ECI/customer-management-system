package com.cms.converter;

import com.cms.model.Country;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for AbstractConverter logic that does not require a JSF FacesContext.
 * A concrete subclass is used to access the abstract class methods.
 */
class AbstractConverterTest {

    /**
     * Concrete subclass used only for testing AbstractConverter.
     */
    private static class CountryTestConverter extends AbstractConverter<Country> {
    }

    private CountryTestConverter converter;

    @BeforeEach
    void setUp() {
        converter = new CountryTestConverter();
    }

    @Test
    void getAsString_withNonNullObject_returnsStringRepresentation() {
        Country country = new Country();
        country.setName("Colombia");

        String result = converter.getAsString(null, null, country);

        assertNotNull(result);
        assertEquals("Colombia", result);
    }

    @Test
    void getAsString_withNullObject_returnsNull() {
        String result = converter.getAsString(null, null, null);

        assertNull(result);
    }

    @Test
    void getAsString_withStringObject_returnsString() {
        String result = converter.getAsString(null, null, "TestValue");

        assertEquals("TestValue", result);
    }

    @Test
    void getAsString_withLongObject_returnsNumericString() {
        String result = converter.getAsString(null, null, 42L);

        assertEquals("42", result);
    }
}
