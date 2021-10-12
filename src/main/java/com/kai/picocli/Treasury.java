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

    public static boolean initialized;

    public static void main(String[] args) {
        checkInitialized();

        if (initialized || args[0].startsWith("init")) {
            int exitCode = new CommandLine(new Treasury()).execute(args);
            System.exit(exitCode);
        } else {
            System.out.println("Please initialize Treasury first. This can be done by running\n\ttreasury init -m");
        }
    }

    private static void checkInitialized() {
        initialized = false;
        //TODO: Update checkInitialized();
    }

    @Override
    public void run() {
        System.out.println("Treasury Command Ran");
    }
}
