package com.shyam.advertiserservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Advertiser implements Serializable{
    @Id
    @GeneratedValue
    private Long id;
    private String advertiserName;
    private String advertiserContactName;
    private Long advertiserCreditLimit;

}
