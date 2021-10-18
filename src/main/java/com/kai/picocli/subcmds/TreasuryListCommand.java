package com.kai.picocli.subcmds;

import com.kai.picocli.TextConstants;
import com.kai.picocli.Treasury;
import picocli.CommandLine;

/**
 * @author Kai Tinkess
 * @version Sep 26, 2021
 */
@CommandLine.Command(name = "list", mixinStandardHelpOptions = true, description = TextConstants.listDescription,
        version = TextConstants.version)
public class TreasuryListCommand implements Runnable {

    @Override
    public void run() {
        if (!Treasury.isInitialized()) {
            System.out.println(TextConstants.notInitializedError);
            return;
        }

        System.out.println("\nList command ran.");
    }
}
