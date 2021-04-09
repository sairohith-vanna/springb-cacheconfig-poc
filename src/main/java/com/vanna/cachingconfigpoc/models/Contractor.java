package com.vanna.cachingconfigpoc.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Contractor extends Person{

    private String partnerCompany;
    private String contractId;
}
