package com.cms.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class UnitModelTest {

    private Unit unit;

    @BeforeEach
    void setUp() {
        unit = new Unit();
    }

    @Test
    void newUnit_createdDateIsSetAutomatically() {
        assertNotNull(unit.getCreatedDate());
    }

    @Test
    void newUnit_personsCollectionIsNotNull() {
        assertNotNull(unit.getPersons(), "Persons collection should be initialized");
    }

    @Test
    void setAndGetAddress() {
        unit.setAddress("123 Main Street");
        assertEquals("123 Main Street", unit.getAddress());
    }

    @Test
    void setAndGetEmail() {
        unit.setEmail("contact@company.com");
        assertEquals("contact@company.com", unit.getEmail());
    }

    @Test
    void setAndGetPhone() {
        unit.setPhone("555-1234");
        assertEquals("555-1234", unit.getPhone());
    }

    @Test
    void setAndGetFax() {
        unit.setFax("555-5678");
        assertEquals("555-5678", unit.getFax());
    }

    @Test
    void setAndGetWebsite() {
        unit.setWebsite("www.company.com");
        assertEquals("www.company.com", unit.getWebsite());
    }

    @Test
    void setAndGetAdditionalInfo() {
        unit.setAdditionalInfo("Some extra information");
        assertEquals("Some extra information", unit.getAdditionalInfo());
    }

    @Test
    void setAndGetName_fromCoreEntity() {
        unit.setName("Acme Corp");
        assertEquals("Acme Corp", unit.getName());
    }

    @Test
    void setAndGetUnitIndustry() {
        UnitIndustry industry = new UnitIndustry();
        industry.setName("Technology");
        unit.setUnitIndustry(industry);
        assertNotNull(unit.getUnitIndustry());
        assertEquals("Technology", unit.getUnitIndustry().getName());
    }

    @Test
    void setAndGetUnitType() {
        UnitType unitType = new UnitType();
        unitType.setName("Client");
        unit.setUnitType(unitType);
        assertNotNull(unit.getUnitType());
        assertEquals("Client", unit.getUnitType().getName());
    }

    @Test
    void setAndGetCountry() {
        Country country = new Country();
        country.setName("Colombia");
        unit.setCountry(country);
        assertNotNull(unit.getCountry());
        assertEquals("Colombia", unit.getCountry().getName());
    }

    @Test
    void setAndGetPersons() {
        Collection<Person> persons = new ArrayList<>();
        Person p = new Person();
        p.setFirstName("Ana");
        persons.add(p);
        unit.setPersons(persons);
        assertEquals(1, unit.getPersons().size());
    }

    @Test
    void address_defaultIsNull() {
        assertNull(unit.getAddress());
    }

    @Test
    void website_defaultIsNull() {
        assertNull(unit.getWebsite());
    }
}
