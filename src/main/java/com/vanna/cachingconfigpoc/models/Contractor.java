package com.vanna.cachingconfigpoc.models;

import com.vanna.cachingconfigpoc.annotations.CacheableClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@CacheableClass
public class Contractor extends Person {

    private String partnerCompany;
    private String contractId;

    @Override
    public String toString() {
        return "Contractor{" +
                "partnerCompany='" + partnerCompany + '\'' +
                ", contractId='" + contractId + '\'' +
                '}';
    }
}
