package com.vanna.cachingconfigpoc.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {

    private String associateId;
    private String name;

    @Override
    public String toString() {
        return "Person{" +
                "associateId='" + associateId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
