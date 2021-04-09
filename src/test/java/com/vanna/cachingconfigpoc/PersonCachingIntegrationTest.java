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

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private CacheableService personService;

    @BeforeEach
    public void setupCacheWithSamplePersonnel() {
        personService.getPersonWithCache("EMPL");
        personService.getPersonWithCache("CONT");
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
}
