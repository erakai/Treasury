package com.kai.picocli.subcmds;

import com.kai.picocli.TextConstants;
import picocli.CommandLine;

import java.util.List;

/**
 * @author Kai Tinkess
 * @version Sep 26, 2021
 */
@CommandLine.Command(name = "store", mixinStandardHelpOptions = true, description = TextConstants.storeDescription)
public class TreasuryStoreCommand implements Runnable {

    @CommandLine.Parameters(index = "0", description = "The identifier this password will be referred to by.")
    private String identifier;

    @CommandLine.Parameters(index = "1", description = "Password", interactive = true)
    private char[] plainPassword;

    @Override
    public void run() {
        System.out.println("Store Command Ran");
        System.out.println("Identifier: " + identifier);
        System.out.println("password: " + plainPassword);
    }
}
