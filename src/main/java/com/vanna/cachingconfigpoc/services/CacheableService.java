package com.vanna.cachingconfigpoc.services;

import com.vanna.cachingconfigpoc.models.Contractor;
import com.vanna.cachingconfigpoc.models.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CacheableService {

    Logger logger = LoggerFactory.getLogger(CacheableService.class);

    public Person getPersonWithoutCache() {
        return new Person(UUID.randomUUID().toString(), "Henry H. Martin");
    }

    @Cacheable(cacheNames = "person", unless = "#result.class == T(com.vanna.cachingconfigpoc.models.Person)")
    public Person getPersonWithCache(String personType) {
        Person companyPerson;
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(personType.equalsIgnoreCase("CONT")) {
            companyPerson = new Contractor();
            companyPerson.setName("Sai Rohith Reddy Vangala");
            companyPerson.setAssociateId(UUID.randomUUID().toString());
            ((Contractor) companyPerson).setContractId(UUID.randomUUID().toString());
            ((Contractor) companyPerson).setPartnerCompany("Vanna InfoTech");
        } else {
            companyPerson = new Person("Sai Rohith Reddy Vangala", UUID.randomUUID().toString());
        }
        return companyPerson;
    }
}
