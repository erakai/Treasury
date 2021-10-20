package com.kai.picocli.subcmds;

import com.kai.db.TSDatabase;
import com.kai.model.AES128;
import com.kai.model.AesUtil;
import com.kai.model.HashController;
import com.kai.model.RijndaelSchedule;
import com.kai.picocli.TextConstants;
import com.kai.picocli.Treasury;
import picocli.CommandLine;

import java.sql.SQLException;
import java.util.Arrays;

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
            if (hexPassword == null || hexPassword.isBlank() || identifier.equals(TextConstants.mainPasswordName)) {
                System.out.println("Identifier '" + identifier + "' not found.");
                return;
            }

            String hexPwd = TSDatabase.instance().retrievePassword(identifier);
            byte[][] bytePassword = AesUtil.singleByteArrayToMatrix(HashController.hexToByte(hexPwd));
            AES128.decrypt(bytePassword, RijndaelSchedule.convertBytesToKey(HashController.hexToByte(hash)));

            System.out.println("The password will be deleted 5 seconds after being printed.");
            System.out.print("Finding password");
            int m = (int)(Math.random() * 8) + 9;
            for (int i = 5; i < m; i++) {
                System.out.print(".");
                Thread.sleep(350);
            }

            String password = AesUtil.blocksToString(bytePassword);
            System.out.println("\n\nPassword for '" + identifier + "':");
            System.out.print(">>\t" + password);
            Thread.sleep(5000);

            System.out.print("\b".repeat(password.length()) + "x".repeat(password.length()));
            System.out.println();

            password = "";
            Arrays.fill(mainPassword, ' ');
        } catch (SQLException | InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
