package com.kai.picocli.subcmds;

import com.kai.picocli.TextConstants;
import com.kai.picocli.Treasury;
import picocli.CommandLine;

/**
 * @author Kai Tinkess
 * @version Oct 12, 2021
 */

@CommandLine.Command(name = "init", description = TextConstants.initDescription, mixinStandardHelpOptions = true,
        sortOptions = false, version = TextConstants.version)
public class TreasuryInitCommand implements Runnable {

    @CommandLine.Option(names = {"-m", "--mainpassword"}, description = TextConstants.mainPasswordDescription,
            prompt = TextConstants.mainPasswordPrompt, interactive = true, required = true)
    private char[] mainPassword;

    @Override
    public void run() {
        System.out.println("Main password hashed, salted, and stored.");
        System.out.println("You can now begin using Treasury. Run\n\ttreasury --help\nfor more information.");
        Treasury.initialized = true;
    }
}
