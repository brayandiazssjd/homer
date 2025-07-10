package com.project.homer.controller.DAOs;

/**
 * DAO
 */
public abstract class DAO {
    
    protected String query;
    
    protected void select(String columns) {
        query += "SELECT " + columns;
    }

    protected void from(String tables) {
        query += "FROM " + tables;
    }

    protected void innerJoinOn(String references) {
        query += "INNER JOIN ON " + references;
    }

    protected void groupBy(String columns) {
        query += "GROUP BY " + columns;
    }

    protected void sortBy(String column) {
        query += "SORT BY " + column;
    }


} 
