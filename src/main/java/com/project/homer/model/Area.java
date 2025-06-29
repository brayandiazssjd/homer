package com.project.homer.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Area
 */

@Getter @Setter @AllArgsConstructor @ToString
public class Area {
    private String name;
    private int id;
    private String description;
}
