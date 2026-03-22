package com.cms.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person();
    }

    @Test
    void newPerson_createdDateIsSetAutomatically() {
        assertNotNull(person.getCreatedDate(), "Created date should be set on construction");
    }

    @Test
    void setAndGetFirstName() {
        person.setFirstName("Juan");
        assertEquals("Juan", person.getFirstName());
    }

    @Test
    void setAndGetLastName() {
        person.setLastName("Pérez");
        assertEquals("Pérez", person.getLastName());
    }

    @Test
    void setAndGetEmail() {
        person.setEmail("juan@example.com");
        assertEquals("juan@example.com", person.getEmail());
    }

    @Test
    void setAndGetPhone() {
        person.setPhone("123456789");
        assertEquals("123456789", person.getPhone());
    }

    @Test
    void setAndGetFax() {
        person.setFax("987654321");
        assertEquals("987654321", person.getFax());
    }

    @Test
    void setAndGetUnit() {
        Unit unit = new Unit();
        unit.setName("Test Unit");
        person.setUnit(unit);
        assertNotNull(person.getUnit());
        assertEquals("Test Unit", person.getUnit().getName());
    }

    @Test
    void setAndGetName_fromCoreEntity() {
        person.setName("Test Person");
        assertEquals("Test Person", person.getName());
    }

    @Test
    void setAndGetNotes_fromCoreEntity() {
        person.setNotes("Some notes");
        assertEquals("Some notes", person.getNotes());
    }

    @Test
    void setAndGetId_fromCoreEntity() {
        person.setId(42L);
        assertEquals(42L, person.getId());
    }

    @Test
    void setAndGetCreatedDate_fromCoreEntity() {
        Date date = new Date();
        person.setCreatedDate(date);
        assertEquals(date, person.getCreatedDate());
    }

    @Test
    void setAndGetEndDate_fromCoreEntity() {
        Date endDate = new Date();
        person.setEndDate(endDate);
        assertEquals(endDate, person.getEndDate());
    }

    @Test
    void toString_returnsName() {
        person.setName("Maria");
        assertEquals("Maria", person.toString());
    }

    @Test
    void firstName_defaultIsNull() {
        assertNull(person.getFirstName());
    }

    @Test
    void email_defaultIsNull() {
        assertNull(person.getEmail());
    }

    @Test
    void unit_defaultIsNull() {
        assertNull(person.getUnit());
    }
}
