package com.vanna.cachingconfigpoc.repository;

import com.vanna.cachingconfigpoc.models.Contractor;
import com.vanna.cachingconfigpoc.models.Person;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class PersonRepository {

    public Person fetchPersonFromStore(String personType) {
        Person companyPerson;
        if (personType.equalsIgnoreCase("CONT")) {
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
