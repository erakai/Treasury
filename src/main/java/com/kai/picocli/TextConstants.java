package com.kai.picocli;

/**
 * @author Kai Tinkess
 * @version Sep 26, 2021
 */
public class TextConstants {
    private TextConstants() {}

    public final static String version = "0.0.1";

    public final static String treasuryDescription = "A password manager utilizing AES-128 encryption.";
    public final static String storeDescription = "Stores a password in the database with a given identifier.";
    public final static String getDescription = "Retrieves a stored password given an identifier.";
    public final static String listDescription = "List all the currently stored password's identifiers.";
    public final static String initDescription = "RUN FIRST: Initialize the program and the master password.";
    public final static String resetDescription = "Delete all existing passwords and the master password. Cannot be undone.";

    public final static String identifierDescription = "The identifier this password will be referred to by.";
    public final static String plainPasswordDescription = "Signifies that a password will be entered.";
    public final static String mainPasswordDescription = "Signifies that the main password will be entered.";

    public final static String plainPasswordPrompt = ">> Enter associated password: ";
    public final static String mainPasswordPrompt = ">> Enter main password: ";
    public final static String createMainPasswordPrompt = ">> Create main password: ";

    public final static String notInitializedError = "Please initialize Treasury first. This can be done by running\n\ttreasury init -m";
    public final static String alreadyInitializedError = "Treasury has already been initialized.";
    public final static String noParamsError = "In order to view possible commands, run:\n\ttreasury --help";
    public final static String noIdentifiersError = "You have no stored passwords! Run:\n\ttreasury store -p [identifier]\nto store one.";
    public final static String loadingDBError = "Database not loaded yet...";
    public final static String incorrectMasterPasswordError = "Your master password was incorrect.";
    public final static String identifierNotFoundError = "Identifier not found.";

    public final static String initRan = "Treasury has been initialized!\nYou can now begin using Treasury. Run\n\ttreasury --help\nfor a list of possible commands.";
    public final static String resetRan = "The database has been cleared. Please initialize it again by running\n\ttreasury init -m";
    public final static String storeRan = "The password has been encrypted and stored in the database with the identifier ";

    public final static String mainPasswordName = "$$MasterPassword";
}
