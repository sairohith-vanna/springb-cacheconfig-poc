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

    public Person fetchPersonFromStoreWithAssociateId(String associateId, String personType) {
        Person companyPerson;
        if (personType.equalsIgnoreCase("CONT")) {
            companyPerson = new Contractor("Vanna Infotech", UUID.randomUUID().toString());
            companyPerson.setName("Sai Rohith Reddy Vangala");
            companyPerson.setAssociateId(associateId);
        } else if (personType.equalsIgnoreCase("PMTE")) {
            companyPerson = new PermanentEmployee("EMP003", LocalDate.now());
            companyPerson.setName("Harry Potter");
            companyPerson.setAssociateId(UUID.randomUUID().toString());
        } else {
            companyPerson = new Person(UUID.randomUUID().toString(), "Sai Rohith Reddy Vangala");
        }
        return companyPerson;
    }

    public Person updatePerson(String associateId, String personName) {
        Person person = fetchPersonFromStoreWithAssociateId(associateId, "CONT");
        person.setName(personName);
        return person;
    }

    public boolean deletePerson() {
        return true;
    }
}
