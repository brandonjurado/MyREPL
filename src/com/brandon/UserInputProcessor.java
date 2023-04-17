package com.brandon;

public class UserInputProcessor {

    private final MyREPL repl;

    public UserInputProcessor(MyREPL repl) {
        this.repl = repl;
    }

    public void process(String userInput) {
        Commands userCommand = Commands.fromString(userInput);
        switch (userCommand) {
            case READ -> repl.readStorage(userInput);
            case WRITE -> repl.storeKeyValue(userInput);
            case DELETE -> repl.deleteFromStorage(userInput);
            case START -> repl.startTransaction();
            case COMMIT -> repl.commitTransaction();
            case ABORT -> repl.abortTransaction();
            case QUIT -> exitApplication();
            case UNKNOWN -> {
                System.out.println("ERROR: Not a valid command. Options: { 'READ {key}' | 'WRITE {key} {value}'" +
                    " | 'DELETE {key}' | 'START' | 'COMMIT' | 'ABORT' | 'QUIT' }");
            }
        }
    }

    private void exitApplication() {
        System.out.println("Exiting...");
        System.exit(0);
    }
}
