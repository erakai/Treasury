package com.kai.picocli.subcmds;

import com.kai.db.TSDatabase;
import com.kai.model.HashController;
import com.kai.picocli.TextConstants;
import com.kai.picocli.Treasury;
import picocli.CommandLine;

import java.sql.SQLException;
import java.util.Arrays;

/**
 * @author Kai Tinkess
 * @version Oct 12, 2021
 */

@CommandLine.Command(name = "init", description = TextConstants.initDescription, mixinStandardHelpOptions = true,
        sortOptions = false, version = TextConstants.version)
public class TreasuryInitCommand implements Runnable {

    @CommandLine.Option(names = {"-m", "--mainpassword"}, description = TextConstants.mainPasswordDescription,
            prompt = TextConstants.createMainPasswordPrompt, interactive = true, required = true)
    private char[] mainPassword;

    @Override
    public void run() {
        if (Treasury.isInitialized()) {
            System.out.println(TextConstants.alreadyInitializedError);
            return;
        }

        TSDatabase.instance().initTable();
        String hash = HashController.byteToHex(HashController.hash(String.valueOf(mainPassword)));

        try {
            TSDatabase.instance().stashHashedMasterPassword(hash);
        } catch (SQLException e) { e.printStackTrace();}
        Treasury.updateInitialized();

        System.out.println(TextConstants.initRan);

        //Clear from memory
        Arrays.fill(mainPassword, ' ');    }
}
