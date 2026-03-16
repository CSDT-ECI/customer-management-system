package com.cms.contextHolder;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HelloWorldTest {

    private HelloWorld helloWorld;

    @BeforeEach
    void setUp() {
        helloWorld = new HelloWorld();
    }

    @Test
    void defaultFirstName_isEmptyString() {
        assertEquals("", helloWorld.getFirstName());
    }

    @Test
    void defaultLastName_isEmptyString() {
        assertEquals("", helloWorld.getLastName());
    }

    @Test
    void showGreeting_withNames_returnsFormattedGreeting() {
        helloWorld.setFirstName("Juan");
        helloWorld.setLastName("Pérez");
        assertEquals("Hi Juan Pérez", helloWorld.showGreeting());
    }

    @Test
    void showGreeting_withEmptyNames_returnsHiWithSpaces() {
        assertEquals("Hi  ", helloWorld.showGreeting());
    }

    @Test
    void setAndGetFirstName() {
        helloWorld.setFirstName("Ana");
        assertEquals("Ana", helloWorld.getFirstName());
    }

    @Test
    void setAndGetLastName() {
        helloWorld.setLastName("García");
        assertEquals("García", helloWorld.getLastName());
    }

    @Test
    void allArgsConstructor_setsFieldsCorrectly() {
        HelloWorld hw = new HelloWorld("María", "López");
        assertEquals("María", hw.getFirstName());
        assertEquals("López", hw.getLastName());
        assertEquals("Hi María López", hw.showGreeting());
    }
}
