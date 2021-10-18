package com.kai.db;

import java.sql.*;
import java.util.ArrayList;
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

    private void executeWithoutResults(String sql) {
        try (Connection conn = DriverManager.getConnection(url)) {
            Statement statement = conn.createStatement();
            statement.execute(sql);
        } catch (SQLException ex) { ex.printStackTrace(); }
    }

    public void initTable() {
        executeWithoutResults( "CREATE TABLE TREASURY (" +
                "Identifier varchar(255)," +
                "EncPwd varchar(255)," +
                "Notes varchar(255)" +
                ");");
    }

    public String getHashedMasterPassword() {
        return retrievePassword("Master!!Password");
    }

    public boolean storeHashedMasterPassword(String hexPassword) {
        if (getHashedMasterPassword() == null) return false;
        executeWithoutResults("INSERT INTO TREASURY (Identifier, EncPwd, Notes" +
                    "VALUES ('Master!!Password', '" + hexPassword + "', 'mp'");
        return true;
    }

    public boolean stashPassword(String identifier, String encryptedPassword, String notes) {
        //TODO: Check if the password already exists
        executeWithoutResults(String.format("INSERT INTO TREASURY (Identifier, EncPwd, Notes" +
                "VALUES ('%s', '%s', '%s", identifier, encryptedPassword, notes));
        return true;
    }

    public String retrievePassword(String identifier) {
        String sql = "SELECT EncPwd, Notes" +
                     "FROM TREASURY WHERE identifier = ?";

        try (Connection conn = DriverManager.getConnection(url)) {
            PreparedStatement pstmt  = conn.prepareStatement(sql);
            pstmt.setString(1, identifier);
            ResultSet rs = pstmt.executeQuery(sql);

            StringBuilder results = new StringBuilder();
            while (rs.next()) {
                results.append(rs.getString("EncPwd"))
                        .append("\t")
                        .append(rs.getString("Notes"))
                        .append("\n");
            }
            return results.toString().trim();
        } catch (SQLException ex) { ex.printStackTrace(); }
        return null;
    }

    public List<String> getIdentifiers() {
        String sql = "SELECT Identifier FROM TREASURY";

        try (Connection conn = DriverManager.getConnection(url)) {
            Statement stmt  = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            List<String> identifiers = new ArrayList<>();
            while (rs.next()) identifiers.add(rs.getString("Identifier"));
            return identifiers;
        } catch (SQLException ex) { ex.printStackTrace(); }
        return null;
    }

}
