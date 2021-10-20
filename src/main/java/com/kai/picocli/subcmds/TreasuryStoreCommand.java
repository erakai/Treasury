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
@CommandLine.Command(name = "store", description = TextConstants.storeDescription, mixinStandardHelpOptions = true,
        sortOptions = false, version = TextConstants.version)
public class TreasuryStoreCommand implements Runnable {

    @CommandLine.Parameters(index = "0", description = TextConstants.identifierDescription)
    private String identifier;

    @CommandLine.Option(names = {"-p", "--password"}, description = TextConstants.plainPasswordDescription,
            prompt = TextConstants.plainPasswordPrompt, interactive = true, required = true)
    private char[] plainPassword;

    @Override
    public void run() {
        if (!Treasury.isInitialized()) {
            System.out.println(TextConstants.notInitializedError);
            return;
        }

        try {
            String stringPwd = String.valueOf(plainPassword);
            byte[][] encPwd = AesUtil.stringToBlocks(stringPwd);
            byte[] key = HashController.hexToByte(TSDatabase.instance().retrieveHashedMasterPassword());
            AES128.encrypt(encPwd, RijndaelSchedule.convertBytesToKey(key));

            System.out.println("Encrypted as: ");
            AesUtil.printByteArray(AesUtil.matrixByteArrayToSingle(encPwd), false);
            String encStringPwd = HashController.byteToHex(AesUtil.matrixByteArrayToSingle(encPwd));
            System.out.println("Hex Form: " + encStringPwd);

            TSDatabase.instance().stashPassword(identifier, encStringPwd, "a password");
            System.out.println(TextConstants.storeRan + "'" + identifier + "'.");
        } catch (SQLException ex) { ex.printStackTrace(); }
        Arrays.fill(plainPassword, ' ');
    }
}
