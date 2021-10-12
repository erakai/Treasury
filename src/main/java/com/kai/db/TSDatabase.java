package com.kai.db;

import java.sql.*;

/**
 * Singleton class that manages the SQLite embedded database.
 *
 * @author Kai Tinkess
 * @version Oct 01, 2021
 */
public class TSDatabase {
    private static TSDatabase instance;
    private TSDatabase() {}

    //TODO: Update with relative path
    private static final String url =
            "jdbc:sqlite:C:/Users/kaiti/Documents/Programming/MediumProjects/Treasury/src/main/resources/treasury.db";

    public static TSDatabase instance() {
        if (instance == null) instance = new TSDatabase();
        return instance;
    }

    public void connect() {
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement statement = conn.createStatement();
            String initSql = "CREATE TABLE TS " +
                    "(IDENT"

        } catch (SQLException ex) { ex.printStackTrace(); }
    }

}
