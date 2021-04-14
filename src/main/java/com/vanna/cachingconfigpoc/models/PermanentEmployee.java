package com.vanna.cachingconfigpoc.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PermanentEmployee extends Person {

    private String employeeId;
    private LocalDate joinDate;
}
