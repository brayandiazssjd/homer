package com.project.homer.model;

import java.util.Date;

import lombok.Data;

/**
 * Consumption
 */

@Data
public class Consumption {

    private String serviceId;
    private Date date;
    private int id;
    private Reservation reservation;
    private User user;
    private Room room;
}
