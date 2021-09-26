package com.kai.picocli.subcmds;

import com.kai.picocli.TextConstants;
import picocli.CommandLine;

/**
 * @author Kai Tinkess
 * @version Sep 26, 2021
 */
@CommandLine.Command(name = "list", mixinStandardHelpOptions = true, description = TextConstants.listDescription)
public class TreasuryListCommand implements Runnable {

    @Override
    public void run() {
        System.out.println("List command ran.");
    }
}
