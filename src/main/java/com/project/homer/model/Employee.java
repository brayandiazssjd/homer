package com.project.homer.model;

import java.util.ArrayList;

import lombok.Data;

/**
 * Employee
 */

@Data
public class Employee extends Person {
    private Position position;
    private ArrayList<Reservation> reservations;
}
