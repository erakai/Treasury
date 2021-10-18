package com.kai.model;

import com.kai.model.AES128;
import com.kai.model.AesUtil;

import java.util.Scanner;

/**
 * @author Kai Tinkess
 * @version Sep 22, 2021
 */
public class EncryptionTester {

    public static void main(String[] args) {
        byte[] bytes = HashController.hash("test");
        AesUtil.printByteArray(bytes, false);
        System.out.println(HashController.byteToHex(bytes));
        AesUtil.printByteArray(HashController.hexToByte(HashController.byteToHex(bytes)), false);

//        Scanner scanner = new Scanner(System.in);
//        while (true) {
//            System.out.println("Enter input: ");
//            String input = scanner.nextLine();
//            System.out.println("Enter key: ");
//            String key = scanner.nextLine();
//            System.out.println("Encrypted:\n" + AES128.encrypt(input, key) + "\n");
//        }
    }

}
