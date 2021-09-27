package com.kai.picocli.subcmds;

import com.kai.picocli.TextConstants;
import picocli.CommandLine;

import java.util.Arrays;

/**
 * @author Kai Tinkess
 * @version Sep 26, 2021
 */
@CommandLine.Command(name = "store", description = TextConstants.storeDescription, mixinStandardHelpOptions = true,
        sortOptions = false, version = "0.0.1")
public class TreasuryStoreCommand implements Runnable {

    @CommandLine.Parameters(index = "0", description = TextConstants.identifierDescription)
    private String identifier;

    @CommandLine.Option(names = {"-p", "--password"}, description = "Signifies that a password will be entered.",
            prompt = TextConstants.passwordPrompt, interactive = true, required = true)
    private char[] plainPassword;

    @Override
    public void run() {
        System.out.println("\nStore Command Ran");
        System.out.println("Identifier: " + identifier);
        System.out.println("password: " + Arrays.toString(plainPassword));
    }
}
