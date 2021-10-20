package com.kai.picocli.subcmds;

import com.kai.db.TSDatabase;
import com.kai.picocli.TextConstants;
import com.kai.picocli.Treasury;
import picocli.CommandLine;

import java.util.List;

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

        List<String> identifiers = TSDatabase.instance().getIdentifiers();
        if (identifiers.size() == 1) {
            System.out.println(TextConstants.noIdentifiersError);
            return;
        }

        System.out.println("Identifiers:");
        for (String ident: identifiers) {
            if (!ident.equals(TextConstants.mainPasswordName)) System.out.println("\t" + ident);
        }
    }
}
