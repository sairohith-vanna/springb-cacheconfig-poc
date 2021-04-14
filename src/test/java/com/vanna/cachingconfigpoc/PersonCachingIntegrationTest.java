package com.vanna.cachingconfigpoc;

import com.vanna.cachingconfigpoc.models.Person;
import com.vanna.cachingconfigpoc.services.CacheableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = CachingConfigPocApplication.class)
public class PersonCachingIntegrationTest {

    private final CacheManager cacheManager;
    private final CacheableService personService;

    @Autowired
    public PersonCachingIntegrationTest(CacheManager cacheManager, CacheableService personService) {
        this.cacheManager = cacheManager;
        this.personService = personService;
    }

    @BeforeEach
    public void setupCacheWithSamplePersonnel() {
        personService.getPersonWithCache("EMPL");
        personService.getPersonWithCache("CONT");
        personService.getPersonWithCache("PMTE");
    }

    @Test
    void retrievedContractorFromCachedMethod_whenRetrievedAgain_shouldBeAvailableInCache() {
        Person person = personService.getPersonWithCache("CONT");
        assertEquals(Optional.of(person), Optional.ofNullable(cacheManager.getCache("person"))
                .map(cx -> cx.get("CONT", Person.class)));
    }

    @Test
    void retrievedPersonFromCachedMethod_whenRetrievedAgain_shouldNotBeAvailableInCache() {
        Person person = personService.getPersonWithCache("EMPL");
        assertEquals(Optional.empty(), Optional.ofNullable(cacheManager.getCache("person"))
                .map(cx -> cx.get("EMPL", Person.class)));
    }

    @Test
    void retrievedContractorFromCachedMethodWithAnnotation_whenRetrievedAgain_shouldBeAvailableInCache() {
        Person person = personService.getPersonWithCacheAnnotation("CONT");
        assertEquals(Optional.of(person), Optional.ofNullable(cacheManager.getCache("person2"))
                .map(cx -> cx.get("CONT", Person.class)));
    }

    @Test
    void retrievedPersonFromCachedMethodWithAnnotation_whenRetrievedAgain_shouldNotBeAvailableInCache() {
        Person person = personService.getPersonWithCacheAnnotation("EMPL");
        assertEquals(Optional.empty(), Optional.ofNullable(cacheManager.getCache("person2"))
                .map(cx -> cx.get("EMPL", Person.class)));
    }

    @Test
    void retrievedPermanentEmployeeFromCacheMethodWithAnnotation_whenRetrievedAgain_shouldNotBeAvailable() {
        Person person = personService.getPersonWithCacheAnnotation("PMTE");
        assertEquals(Optional.empty(), Optional.ofNullable(cacheManager.getCache("person2"))
                .map(pecx -> pecx.get("PMTE", Person.class)));
    }
}
