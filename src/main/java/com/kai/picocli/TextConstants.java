package com.kai.picocli;

/**
 * @author Kai Tinkess
 * @version Sep 26, 2021
 */
public class TextConstants {
    private TextConstants() {}

    public final static String treasuryDescription = "A password manager utilizing AES-128 encryption.";
    public final static String storeDescription = "Stores a password in the database with a given identifier.";
    public final static String getDescription = "Retrieves a stored password given an identifier.";
    public final static String listDescription = "List all the currently stored password's identifiers.";

    public final static String identifierDescription = "The identifier this password will be referred to by.";

    public final static String passwordPrompt = "|| Enter associated password: ";
}
