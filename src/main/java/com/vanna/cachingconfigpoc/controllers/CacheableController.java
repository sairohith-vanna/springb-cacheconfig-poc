package com.vanna.cachingconfigpoc.controllers;

import com.vanna.cachingconfigpoc.models.Person;
import com.vanna.cachingconfigpoc.services.CacheableService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/employees")
public class CacheableController {

    private final CacheableService service;

    public CacheableController(CacheableService service) {
        this.service = service;
    }

    @GetMapping("get")
    public ResponseEntity<Person> getEmployeeWithData() {
        Person newPerson = service.getPersonWithoutCache();
        return ResponseEntity.ok(newPerson);
    }

    @GetMapping("cached/get/{personType}")
    public ResponseEntity<Person> getEmployeeWithDataCached(@PathVariable("personType") String personType) {
        Person newPerson = service.getPersonWithCache(personType);
        return ResponseEntity.ok(newPerson);
    }
}
