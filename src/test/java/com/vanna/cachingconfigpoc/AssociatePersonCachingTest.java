package com.vanna.cachingconfigpoc;

import com.github.benmanes.caffeine.cache.LoadingCache;
import com.vanna.cachingconfigpoc.models.Person;
import com.vanna.cachingconfigpoc.services.CacheableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class AssociatePersonCachingTest {

    private final CacheManager cacheManager;
    private final CacheableService personService;
    private Person person;

    @Autowired
    public AssociatePersonCachingTest(CacheManager cacheManager, CacheableService personService) {
        this.cacheManager = cacheManager;
        this.personService = personService;
    }

    @BeforeEach
    public void setupCacheWithAssociatePerson() {
        person = personService.getAssociatedPersonWithCacheAnnotation("17a36637-04d4-4357-9c6b-10a755dbbcdd");
    }

    /**
     * Checks a contractors availability in the cache
     */
    @Test
    public void retrievedContractor_whenRetrievedAgain_shouldBeAvailableInCache() {
        assertEquals(Optional.of(person), Optional.ofNullable(cacheManager.getCache("person2"))
                .map(cache -> cache.get("17a36637-04d4-4357-9c6b-10a755dbbcdd", Person.class)));
    }

    /**
     * Tests for cache update, using CachePut annotation.
     * Updates the contractors name of the predefined associateId,
     * and checks if the name reflects in the cache
     */
    @Test
    public void cachedContractor_whenUpdated_shouldBeUpdatedAndAvailableInCache() {
        Person person = personService.updatePerson("Name, Updated", "17a36637-04d4-4357-9c6b-10a755dbbcdd");
        assertEquals("Name, Updated", Optional.ofNullable(cacheManager.getCache("person2"))
                .map(cache -> cache.get("17a36637-04d4-4357-9c6b-10a755dbbcdd", Person.class))
                .orElse(new Person()).getName());
    }

    /**
     * Tests for cache eviction, using CacheEvict annotation.
     * Performs a mock delete, but allows removal of the person from the cache.
     * Checks if the person is still available in the cache
     */
    @Test
    public void cachedContractor_whenDeleted_shouldNotBeAvailableInCache() {
        personService.deletePerson("17a36637-04d4-4357-9c6b-10a755dbbcdd");
        assertEquals(Optional.empty(), Optional.ofNullable(cacheManager.getCache("person2"))
                .map(cache -> cache.get("17a36637-04d4-4357-9c6b-10a755dbbcdd", Person.class)));
    }
}
