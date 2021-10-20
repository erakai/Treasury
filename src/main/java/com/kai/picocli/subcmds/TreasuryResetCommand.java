package com.kai.picocli.subcmds;

import com.kai.db.TSDatabase;
import com.kai.picocli.TextConstants;
import picocli.CommandLine;

/**
 * @author Kai Tinkess
 * @version Oct 20, 2021
 */
@CommandLine.Command(name = "reset", description = TextConstants.resetDescription, mixinStandardHelpOptions = true,
        sortOptions = false, version = TextConstants.version)
public class TreasuryResetCommand implements Runnable {

    @Override
    public void run() {
        TSDatabase.instance().resetTable();
        System.out.println(TextConstants.resetRan);
    }

}
