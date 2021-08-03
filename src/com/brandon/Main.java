package com.brandon;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main implements MyREPL {

	private static Map<Integer, Map<String, String>> nestedTransaction;
	private static Map<String, String> currentTransaction;
	private static Integer currentNestedTransactionIndex;
	private static final String SPLIT_STRING = "\\s+";

    public static void main(String[] args) {
		nestedTransaction = new HashMap<>();
		currentTransaction = new HashMap<>();
		currentNestedTransactionIndex = 0;

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
				case READ -> readStorage(userInput);
				case WRITE -> storeKeyValue(userInput);
				case DELETE -> deleteFromStorage(userInput);
				case START -> startTransaction();
				case COMMIT -> commitTransaction();
				case ABORT -> abortTransaction();
				case QUIT -> exitApplication();
				case UNKNOWN ->
						System.out.println("ERROR: Not a valid command. Options: { 'READ {key}' | 'WRITE {key} {value}'" +
								" | 'DELETE {key}' | 'START' | 'COMMIT' | 'ABORT' | 'QUIT' }");
			}
		}
	}

	private static void storeKeyValue(String keyValueString) {
		String[] keyValue = keyValueString.split(SPLIT_STRING);
		if (keyValue.length <= 1) {
			System.out.println("ERROR: Key & Value input are not present.");
		} else if (keyValue.length == 2) {
			System.out.println("ERROR: Value is not present for key '" + keyValue[1] + "'.");
		} else {
			currentTransaction.put(keyValue[1], keyValue[2]);
			nestedTransaction.put(currentNestedTransactionIndex, currentTransaction);
		}
	}

	private static void readStorage(String input) {
    	String[] keyInput = input.split(SPLIT_STRING);
    	if (keyInput.length <= 1) {
    		System.out.println("ERROR: Value is not present.");
		} else {
    		String key = keyInput[1];
			if (currentTransaction.containsKey(key)) {
				System.out.println(currentTransaction.get(key));
			} else {
				System.out.println("Key not found: " + key);
			}
		}
	}

	private static void startTransaction() {
    	currentTransaction = new HashMap<>();
    	currentTransaction.putAll(nestedTransaction.get(currentNestedTransactionIndex));
    	currentNestedTransactionIndex++;
    	nestedTransaction.put(currentNestedTransactionIndex, currentTransaction);
	}

	private static void commitTransaction() {
    	if (currentNestedTransactionIndex > 0) {
			nestedTransaction.remove(currentNestedTransactionIndex);
    		currentNestedTransactionIndex--;
    		nestedTransaction.put(currentNestedTransactionIndex, currentTransaction);
    		currentTransaction = new HashMap<>();
    		currentTransaction.putAll(nestedTransaction.get(currentNestedTransactionIndex));
		} else {
    		System.out.println("ERROR: There is no active transaction.");
		}
	}

	private static void abortTransaction() {
    	if (currentNestedTransactionIndex == 0) {
    		System.out.println("ERROR: Not within a transaction to abort.");
    		return;
		}
    	nestedTransaction.remove(currentNestedTransactionIndex);
    	currentNestedTransactionIndex--;
		currentTransaction = new HashMap<>();
    	currentTransaction.putAll(nestedTransaction.get(currentNestedTransactionIndex));
	}

	private static void deleteFromStorage(String input) {
		String key = input.split(SPLIT_STRING)[1];
		Map<String, String> keyValue = nestedTransaction.get(currentNestedTransactionIndex);
		if (keyValue != null) {
			keyValue.remove(key);
		} else {
			System.out.println("ERROR: Unable to delete, key does not exist.");
		}
	}

	private static void exitApplication() {
    	System.out.println("Exiting...");
    	System.exit(0);
	}
}
