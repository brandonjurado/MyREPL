package com.brandon;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class StorageManager implements MyREPL {

    private final Stack<Map<String, String>> transactionStack;
    private Map<String, String> currentTransaction;
    private final String SPLIT_STRING = "\\s+";

    public StorageManager() {
        transactionStack = new Stack<>();
        currentTransaction = new HashMap<>();
    }

    @Override
    public void storeKeyValue(String keyValueString) {
        String[] keyValue = keyValueString.split(SPLIT_STRING);
        if (keyValue.length <= 1) {
            System.out.println("ERROR: Key & Value input are not present.");
        } else if (keyValue.length == 2) {
            System.out.println("ERROR: Value is not present for key '" + keyValue[1] + "'.");
        } else {
            currentTransaction.put(keyValue[1], keyValue[2]);
        }
    }

    @Override
    public void readStorage(String input) {
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

    @Override
    public void startTransaction() {
        transactionStack.push(currentTransaction);
        currentTransaction = new HashMap<>(currentTransaction);
    }

    @Override
    public void commitTransaction() {
        if (transactionStack.isEmpty()) {
            System.out.println("ERROR: There is no active transaction.");
            return;
        }
        transactionStack.pop();
        if (!transactionStack.isEmpty()) {
            currentTransaction = transactionStack.peek();
        } else {
            currentTransaction = new HashMap<>();
            transactionStack.push(currentTransaction);
        }
    }

    @Override
    public void abortTransaction() {
        if (transactionStack.isEmpty()) {
            System.out.println("ERROR: There is no active transaction.");
            return;
        }
        currentTransaction = transactionStack.pop();
    }

    @Override
    public void deleteFromStorage(String input) {
        String key = input.split(SPLIT_STRING)[1];
        if (currentTransaction.containsKey(key)) {
            currentTransaction.remove(key);
        } else {
            System.out.println("ERROR: Unable to delete, key does not exist.");
        }
    }
}
