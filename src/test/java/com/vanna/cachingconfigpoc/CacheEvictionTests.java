package com.vanna.cachingconfigpoc;

import com.vanna.cachingconfigpoc.models.Person;
import com.vanna.cachingconfigpoc.services.CacheableService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.CacheManager;

import java.util.Optional;

import static java.util.Optional.ofNullable;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class CacheEvictionTests {

    private final CacheableService personService;
    private final CacheManager cacheManager;

    @Autowired
    public CacheEvictionTests(CacheableService personService, CacheManager cacheManager) {
        this.personService = personService;
        this.cacheManager = cacheManager;
    }

    @BeforeEach
    public void setupCacheWithAssociatePerson() {
        personService.getAssociatedPersonWithCacheAnnotation("17a36637-04d4-4357-9c6b-10a755dbbcdd");
    }

    @Test
    public void existingRetrievedContractor_whenNewContractorRetrieved_shouldBeEvictedFromCache() throws InterruptedException {
        personService.getAssociatedPersonWithCacheAnnotation("17a36637-04d4-4357-9c6b-10a755dbbcde");
        Thread.sleep(100);
        assertEquals(Optional.empty(),
                ofNullable(cacheManager.getCache("person2"))
                        .map(cacheX -> cacheX.get("17a36637-04d4-4357-9c6b-10a755dbbcdd", Person.class)));
    }

    @Test
    public void existingRetrievedContractor_whenRetrievedAfters5Seconds_shouldHaveBeenEvictedFromCache() throws InterruptedException {
        Thread.sleep(2000);
        assertEquals(Optional.empty(),
                Optional.ofNullable(cacheManager.getCache("person2")).map(cacheX -> cacheX.get("17a36637-04d4-4357-9c6b-10a755dbbcdd", Person.class)));
    }
}
