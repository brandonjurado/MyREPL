package com.brandon;

import java.util.Scanner;

public class Main {

    private static StorageManager storageManager;

    public static void main(String[] args) {
        storageManager = new StorageManager();

	    Scanner reader = new Scanner(System.in);
	    System.out.println("Application started...");
	    processUserInput(reader);
    }

    private static void processUserInput(Scanner reader) {
    	String userInput;
    	Commands userCommand;
		while (reader.hasNext()) {
			userInput = reader.nextLine();
			userCommand = Commands.ifContains(userInput);
			switch(userCommand) {
				case READ -> storageManager.readStorage(userInput);
				case WRITE -> storageManager.storeKeyValue(userInput);
				case DELETE -> storageManager.deleteFromStorage(userInput);
				case START -> storageManager.startTransaction();
				case COMMIT -> storageManager.commitTransaction();
				case ABORT -> storageManager.abortTransaction();
				case QUIT -> exitApplication();
				case UNKNOWN ->
						System.out.println("ERROR: Not a valid command. Options: { 'READ {key}' | 'WRITE {key} {value}'" +
								" | 'DELETE {key}' | 'START' | 'COMMIT' | 'ABORT' | 'QUIT' }");
			}
		}
	}

	private static void exitApplication() {
    	System.out.println("Exiting...");
    	System.exit(0);
	}
}
