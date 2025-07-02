package com.project.homer.model;

import lombok.Data;

/**
 * Position
 */
@Data
public class Position {
    private String name;
    private int id;
    private String description;
    private Area area;
}
