package com.kai.main;

import com.kai.model.AES128;

import java.util.Scanner;

/**
 * @author Kai Tinkess
 * @version Sep 22, 2021
 */
public class Runner {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter input: ");
            String input = scanner.nextLine();
            System.out.println("Enter key: ");
            String key = scanner.nextLine();
            System.out.println("Encrypted:\n" + AES128.encrypt(input, key) + "\n");
        }
    }
}
