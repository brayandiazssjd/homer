package com.project.homer.model;

import java.util.Date;

import lombok.Data;

/**
 * Reservation
 */

@Data
public class Reservation {

    private int id;
    private Date entry;
    private Date departure;
    private String roomId;
    private String userId;
}
