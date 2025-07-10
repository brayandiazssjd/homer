package com.project.homer.model;

import lombok.Data;

/**
 * User
 */

@Data
public class User extends Person {
    private Reservation reservation;
    
}
