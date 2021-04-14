package com.vanna.cachingconfigpoc.repository;

import com.vanna.cachingconfigpoc.models.Contractor;
import com.vanna.cachingconfigpoc.models.PermanentEmployee;
import com.vanna.cachingconfigpoc.models.Person;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public class PersonRepository {

    public Person fetchPersonFromStore(String personType) {
        Person companyPerson;
        if (personType.equalsIgnoreCase("CONT")) {
            companyPerson = new Contractor(UUID.randomUUID().toString(), "Vanna Infotech");
            companyPerson.setName("Sai Rohith Reddy Vangala");
            companyPerson.setAssociateId(UUID.randomUUID().toString());
        } else if (personType.equalsIgnoreCase("PMTE")) {
            companyPerson = new PermanentEmployee("EMP003", LocalDate.now());
            companyPerson.setName("Harry Potter");
            companyPerson.setAssociateId(UUID.randomUUID().toString());
        } else {
            companyPerson = new Person(UUID.randomUUID().toString(), "Sai Rohith Reddy Vangala");
        }
        return companyPerson;
    }
}
