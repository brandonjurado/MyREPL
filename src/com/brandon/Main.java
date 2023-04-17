package com.brandon;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        MyREPL storageManager = new StorageManager();
        UserInputProcessor processor = new UserInputProcessor(storageManager);

        System.out.println("Application started...");
        try (Scanner reader = new Scanner(System.in)) {
            while (reader.hasNext()) {
                String userInput = reader.nextLine();
                processor.process(userInput);
            }
        }
    }
}
