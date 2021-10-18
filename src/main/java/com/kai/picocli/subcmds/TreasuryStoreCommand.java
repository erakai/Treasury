package com.kai.picocli.subcmds;

import com.kai.picocli.TextConstants;
import com.kai.picocli.Treasury;
import picocli.CommandLine;

import java.util.Arrays;

/**
 * @author Kai Tinkess
 * @version Sep 26, 2021
 */
@CommandLine.Command(name = "store", description = TextConstants.storeDescription, mixinStandardHelpOptions = true,
        sortOptions = false, version = TextConstants.version)
public class TreasuryStoreCommand implements Runnable {

    @CommandLine.Parameters(index = "0", description = TextConstants.identifierDescription)
    private String identifier;

    @CommandLine.Option(names = {"-p", "--password"}, description = TextConstants.plainPasswordDescription,
            prompt = TextConstants.plainPasswordPrompt, interactive = true, required = true)
    private char[] plainPassword;

    @Override
    public void run() {
        if (!Treasury.isInitialized()) {
            System.out.println(TextConstants.notInitializedError);
            return;
        }

        //Make sure to sanitize identifier
        System.out.println("\nStore Command Ran");
        System.out.println("Identifier: " + identifier);
        System.out.println("password: " + Arrays.toString(plainPassword));
    }
}
