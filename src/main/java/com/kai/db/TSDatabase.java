package com.kai.db;

import java.sql.*;
import java.util.List;

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
            String initSql = "CREATE TABLE TREASURY (" +
                                "Identifier varchar(255)," +
                                "EncPwd varchar(255)," +
                                "Notes varchar(255)" +
                             ");";
            statement.execute(initSql);
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    public String getHashedMasterPassword() {
        return null;
    }

    public boolean storeHashedMasterPassword(String hexPassword) {
        return false;
    }

    public boolean stashPassword(String identifier, String encryptedPassword) {
        return false;
    }

    public String retrievePassword(String identifier) {
        return null;
    }

    public List<String> getIdentifiers() {
        return null;
    }

}
