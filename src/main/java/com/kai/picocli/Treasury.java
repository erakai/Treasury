package com.kai.picocli;

import com.kai.db.TSDatabase;
import com.kai.picocli.subcmds.*;
import picocli.CommandLine;

/**
 * @author Kai Tinkess
 * @version Sep 26, 2021
 */
@CommandLine.Command(name = "treasury", aliases = {"ts", "treas"}, mixinStandardHelpOptions = true,
        version = ("Treasury " + TextConstants.version), description = TextConstants.treasuryDescription,
        subcommands = {
                TreasuryInitCommand.class,
                TreasuryListCommand.class,
                TreasuryGetCommand.class,
                TreasuryStoreCommand.class,
                TreasuryResetCommand.class
        })
public class Treasury implements Runnable {

    private static boolean initialized = false;

    public static void main(String[] args) {
        //TODO: Sanitize inputs

        updateInitialized();
        int exitCode = new CommandLine(new Treasury()).execute(args);
        System.exit(exitCode);
    }

    public static void updateInitialized() {
        try {
            initialized = (TSDatabase.instance().retrieveHashedMasterPassword() != null);
        } catch (Exception ex) {
            System.out.println(TextConstants.loadingDBError);
//            ex.printStackTrace();
        }
    }

    public static boolean isInitialized() {
        return initialized;
    }

    @Override
    public void run() {
        System.out.println(TextConstants.noParamsError);
    }
}
