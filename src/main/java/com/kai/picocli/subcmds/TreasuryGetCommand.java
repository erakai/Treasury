package com.kai.picocli.subcmds;

import com.kai.picocli.TextConstants;
import picocli.CommandLine;

/**
 * @author Kai Tinkess
 * @version Sep 26, 2021
 */
@CommandLine.Command(name = "get", mixinStandardHelpOptions = true, description = TextConstants.getDescription)
public class TreasuryGetCommand implements Runnable {

    @Override
    public void run() {
        System.out.println("Get command ran.");
    }
}
