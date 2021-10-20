package com.kai.picocli.subcmds;

import com.kai.db.TSDatabase;
import com.kai.model.AES128;
import com.kai.model.AesUtil;
import com.kai.model.HashController;
import com.kai.picocli.TextConstants;
import com.kai.picocli.Treasury;
import picocli.CommandLine;

import java.sql.SQLException;

/**
 * @author Kai Tinkess
 * @version Sep 26, 2021
 */
@CommandLine.Command(name = "get", mixinStandardHelpOptions = true, description = TextConstants.getDescription,
    sortOptions = false, version = TextConstants.version)
public class TreasuryGetCommand implements Runnable {

    @CommandLine.Parameters(index = "0", description = TextConstants.identifierDescription)
    private String identifier;

    @CommandLine.Option(names = {"-m", "--mainpassword"}, description = TextConstants.mainPasswordDescription,
            prompt = TextConstants.mainPasswordPrompt, interactive = true, required = true)
    private char[] mainPassword;

    @Override
    public void run() {
        if (!Treasury.isInitialized()) {
            System.out.println(TextConstants.notInitializedError);
            return;
        }

        try {
            String hash = HashController.byteToHex(HashController.hash(String.valueOf(mainPassword)));
            if (!hash.equals(TSDatabase.instance().retrieveHashedMasterPassword())) {
                System.out.println(TextConstants.incorrectMasterPasswordError);
                return;
            }

            String hexPassword = TSDatabase.instance().retrievePassword(identifier);
            if (hexPassword == null || identifier.equals(TextConstants.mainPasswordName)) {
                System.out.println(TextConstants.identifierNotFoundError);
                return;
            }

            byte[][] bytePassword = AesUtil.singleByteArrayToMatrix(HashController.hexToByte(TSDatabase.instance().retrievePassword(identifier)));
            AES128.decrypt(bytePassword, HashController.hexToByte(hash));
            String password = AesUtil.blocksToString(bytePassword);

            System.out.print("Finding password");
            for (int i = 0; i < Math.random() * 8; i++) {
                System.out.print(".");
                Thread.sleep(166);
            }
            System.out.println("The password will be deleted 5 seconds after being printed.");
            System.out.println("\nPrinting password for '" + identifier + "' in 2 seconds:");
            Thread.sleep(2000);
            System.out.println("\n\t" + password);
            Thread.sleep(5000);
            for (int i = 0; i < password.length(); i++) System.out.print("\b");
            System.out.println();

        } catch (SQLException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
