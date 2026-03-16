package com.cms.service;

import com.cms.model.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.repository.CrudRepository;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PersonService, exercising the inherited logic in AbstractService.
 * The CrudRepository is mocked so no database connection is needed.
 */
@SuppressWarnings({"unchecked", "deprecation"})
class PersonServiceTest {

    @Mock
    private CrudRepository repository;

    @InjectMocks
    private PersonService personService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void save_delegatesToRepository() {
        Person person = new Person();
        person.setFirstName("Juan");
        when(repository.save(person)).thenReturn(person);

        Person result = personService.save(person);

        assertNotNull(result);
        verify(repository, times(1)).save(person);
    }

    @Test
    void findAll_delegatesToRepository() {
        List<Person> persons = Arrays.asList(new Person(), new Person());
        when(repository.findAll()).thenReturn(persons);

        Iterable<Person> result = personService.findAll();

        assertNotNull(result);
        verify(repository, times(1)).findAll();
    }

    @Test
    void findById_delegatesToRepository() {
        Person person = new Person();
        person.setId(1L);
        when(repository.findOne(1L)).thenReturn(person);

        Person result = personService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository, times(1)).findOne(1L);
    }

    @Test
    void delete_delegatesToRepository() {
        doNothing().when(repository).delete(anyLong());

        personService.delete(1L);

        verify(repository, times(1)).delete(1L);
    }

    @Test
    void save_returnsPersistedEntity() {
        Person person = new Person();
        person.setFirstName("Maria");
        person.setEmail("maria@test.com");
        when(repository.save(any(Person.class))).thenReturn(person);

        Person saved = personService.save(person);

        assertEquals("Maria", saved.getFirstName());
        assertEquals("maria@test.com", saved.getEmail());
    }
}
