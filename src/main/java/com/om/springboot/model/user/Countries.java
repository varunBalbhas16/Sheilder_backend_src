package com.om.springboot.model.user;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "countries")
public class Countries {
    @Id
    private int countryId;
    private String country;
    private int status;

}
