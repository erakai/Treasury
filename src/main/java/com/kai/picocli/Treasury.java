package com.kai.picocli;

import com.kai.picocli.subcmds.*;
import picocli.CommandLine;

/**
 * @author Kai Tinkess
 * @version Sep 26, 2021
 */
@CommandLine.Command(name = "treasury", aliases = {"ts", "treas"}, mixinStandardHelpOptions = true,
        version = "Treasury 0.0.1", description = TextConstants.treasuryDescription,
        subcommands = {
                TreasuryListCommand.class,
                TreasuryGetCommand.class,
                TreasuryStoreCommand.class,
        })
public class Treasury implements Runnable {

    public static void main(String[] args) {
        int exitCode = new CommandLine(new Treasury()).execute(args);
        System.exit(exitCode);
    }

    @Override
    public void run() {
        System.out.println("Treasury Command Ran");
    }
}
