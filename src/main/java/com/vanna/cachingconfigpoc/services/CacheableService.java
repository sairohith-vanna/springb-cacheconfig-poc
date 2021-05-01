package com.vanna.cachingconfigpoc.services;

import com.vanna.cachingconfigpoc.models.Person;
import com.vanna.cachingconfigpoc.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CacheableService {

    private static final Logger logger = LoggerFactory.getLogger(CacheableService.class);
    private final PersonRepository personRepository;

    @Autowired
    public CacheableService(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public Person getPersonWithoutCache() {
        return new Person(UUID.randomUUID().toString(), "Henry H. Martin");
    }

    @Cacheable(cacheNames = "person", unless = "#result.class == T(com.vanna.cachingconfigpoc.models.Person)")
    public Person getPersonWithCache(String personType) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Person person = personRepository.fetchPersonFromStore(personType);
        logger.info("RETRIEVED PERSON FROM CLASS BASED CACHE INCLUSION::");
        logger.info(person.toString());
        return person;
    }

    @Cacheable(
            cacheNames = "person2",
            unless = "#result.class.getAnnotation(T(com.vanna.cachingconfigpoc.annotations.CacheableClass)) == null"
    )
    public Person getPersonWithCacheAnnotation(String personType) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Person person = personRepository.fetchPersonFromStore(personType);
        logger.info("RETRIEVED PERSON FROM ANNOTATION BASED CACHE INCLUSION::");
        logger.info(person.toString());
        return person;
    }

    @Cacheable(
            cacheNames = "person2",
            unless = "#result.class.getAnnotation(T(com.vanna.cachingconfigpoc.annotations.CacheableClass)) == null",
            key = "#associateId"
    )
    public Person getAssociatedPersonWithCacheAnnotation(String associateId) {
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Person person = personRepository.fetchPersonFromStoreWithAssociateId(associateId, "CONT");
        logger.info("RETRIEVED PERSON FROM ANNOTATION BASED CACHE INCLUSION::");
        logger.info(person.toString());
        return person;
    }

    @CachePut(
            cacheNames = "person2",
            key = "#associateId",
            unless = "#result.class.getAnnotation(T(com.vanna.cachingconfigpoc.annotations.CacheableClass)) == null"
    )
    public Person updatePerson(String personName, String associateId) {
        return personRepository.updatePerson(associateId, personName);
    }

    @CacheEvict(key = "#associateId", cacheNames = "person2")
    public void deletePerson(String associateId) {
        personRepository.deletePerson();
    }
}
