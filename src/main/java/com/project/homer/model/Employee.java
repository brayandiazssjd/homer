package com.project.homer.model;

import java.util.ArrayList;

import lombok.Data;

/**
 * Employee
 */

@Data
public class Employee extends Person {
    private Cargo cargo;
    private ArrayList<Reservation> reservations;
}
