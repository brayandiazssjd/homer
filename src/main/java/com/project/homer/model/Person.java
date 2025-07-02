package com.project.homer.model;

import lombok.Data;

/**
 * Person
 */
@Data
public class Person {
    private String id;
    private String names;
    private String surnames;
    private long telephone;
    private String address;
    private String email;
}
