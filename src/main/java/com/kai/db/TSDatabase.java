package com.kai.db;

import com.kai.picocli.TextConstants;
import com.kai.picocli.Treasury;

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
            "jdbc:sqlite:Documents/Programming/MediumProjects/Treasury/src/main/resources/treasury.db";

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
        executeWithoutResults( "CREATE TABLE Treasury (" +
                "Identifier varchar(255)," +
                "EncPwd varchar(255)," +
                "Notes varchar(255)" +
                ");");
    }

    public void resetTable() {
        executeWithoutResults("DROP TABLE Treasury");
    }

    public String retrieveHashedMasterPassword() throws SQLException {
        return retrievePassword(TextConstants.mainPasswordName);
    }

    public boolean stashHashedMasterPassword(String hexPassword) throws SQLException {
        if (retrieveHashedMasterPassword() == null) return false;
        executeWithoutResults("INSERT INTO Treasury (Identifier, EncPwd, Notes)" +
                    "VALUES ('" + TextConstants.mainPasswordName + "', '" + hexPassword + "', 'The Master password')");
        return true;
    }

    public boolean stashPassword(String identifier, String encryptedPassword, String notes) {
        //TODO: Check if the password already exists
        executeWithoutResults(String.format("INSERT INTO Treasury (Identifier, EncPwd, Notes" +
                "VALUES ('%s', '%s', '%s", identifier, encryptedPassword, notes));
        return true;
    }

    public String retrievePassword(String identifier) throws SQLException {
        String sql = "SELECT EncPwd " +
                     "FROM Treasury WHERE identifier = ?";

        Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt  = conn.prepareStatement(sql);
        pstmt.setString(1, identifier);
        ResultSet rs = pstmt.executeQuery();

        StringBuilder results = new StringBuilder();
        while (rs.next()) {
            results.append(rs.getString("EncPwd"));
        }
        return results.toString().trim();
    }

    public List<String> getIdentifiers() {
        String sql = "SELECT Identifier FROM Treasury";

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
