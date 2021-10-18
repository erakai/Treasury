package com.kai.picocli;

import com.kai.picocli.subcmds.*;
import picocli.CommandLine;

/**
 * @author Kai Tinkess
 * @version Sep 26, 2021
 */
@CommandLine.Command(name = "treasury", aliases = {"ts", "treas"}, mixinStandardHelpOptions = true,
        version = ("Treasury " + TextConstants.version), description = TextConstants.treasuryDescription,
        subcommands = {
                TreasuryListCommand.class,
                TreasuryGetCommand.class,
                TreasuryStoreCommand.class,
                TreasuryInitCommand.class,
        })
public class Treasury implements Runnable {

    private static boolean initialized;

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Treasury()).execute(args);
        System.exit(exitCode);
    }

    public static void checkInitialized() {
        initialized = false;
        //TODO: Update checkInitialized();
    }

    public static boolean isInitialized() {
        return initialized;
    }

    @Override
    public void run() {
        System.out.println("Treasury Command Ran");
    }
}
