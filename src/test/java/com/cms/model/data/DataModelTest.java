package com.cms.model.data;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataModelTest {

    @Test
    void firstName_canBeInstantiated() {
        FirstName firstName = new FirstName();
        assertNotNull(firstName);
        assertNotNull(firstName.getCreatedDate());
    }

    @Test
    void firstName_setAndGetName() {
        FirstName firstName = new FirstName();
        firstName.setName("Carlos");
        assertEquals("Carlos", firstName.getName());
    }

    @Test
    void firstName_toStringReturnsName() {
        FirstName firstName = new FirstName();
        firstName.setName("Luis");
        assertEquals("Luis", firstName.toString());
    }

    @Test
    void lastName_canBeInstantiated() {
        LastName lastName = new LastName();
        assertNotNull(lastName);
        assertNotNull(lastName.getCreatedDate());
    }

    @Test
    void lastName_setAndGetName() {
        LastName lastName = new LastName();
        lastName.setName("González");
        assertEquals("González", lastName.getName());
    }

    @Test
    void lastName_toStringReturnsName() {
        LastName lastName = new LastName();
        lastName.setName("Ramírez");
        assertEquals("Ramírez", lastName.toString());
    }
}
