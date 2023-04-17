package com.brandon;

import java.util.Scanner;

public class Main {

    private static MyREPL repl;

    public static void main(String[] args) {
        repl = new StorageManager();

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
				case READ -> repl.readStorage(userInput);
				case WRITE -> repl.storeKeyValue(userInput);
				case DELETE -> repl.deleteFromStorage(userInput);
				case START -> repl.startTransaction();
				case COMMIT -> repl.commitTransaction();
				case ABORT -> repl.abortTransaction();
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
