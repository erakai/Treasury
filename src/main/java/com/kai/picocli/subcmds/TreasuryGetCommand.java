package com.kai.picocli.subcmds;

import com.kai.picocli.TextConstants;
import picocli.CommandLine;

import java.util.Arrays;

/**
 * @author Kai Tinkess
 * @version Sep 26, 2021
 */
@CommandLine.Command(name = "get", mixinStandardHelpOptions = true, description = TextConstants.getDescription,
    sortOptions = false, version = "0.0.1")
public class TreasuryGetCommand implements Runnable {

    @CommandLine.Parameters(index = "0", description = TextConstants.identifierDescription)
    private String identifier;

    @CommandLine.Option(names = {"-m", "--mainpassword"}, description = TextConstants.mainPasswordDescription,
            prompt = TextConstants.mainPasswordPrompt, interactive = true, required = true)
    private char[] mainPassword;

    @Override
    public void run() {
        System.out.println("\nGet Command Ran");
        System.out.println("Identifier: " + identifier);
        System.out.println("Main Password: " + Arrays.toString(mainPassword));    }
}
