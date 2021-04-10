package com.vanna.cachingconfigpoc.models;

import com.vanna.cachingconfigpoc.annotations.CacheableClass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@CacheableClass
public class Contractor extends Person{

    private String partnerCompany;
    private String contractId;
}
