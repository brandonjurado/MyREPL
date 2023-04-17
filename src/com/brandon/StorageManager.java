package com.brandon;

import java.util.HashMap;
import java.util.Map;

public class StorageManager implements MyREPL {

    private Map<Integer, Map<String, String>> nestedTransaction;
    private Map<String, String> currentTransaction;
    private Integer currentNestedTransactionIndex;
    private final String SPLIT_STRING = "\\s+";

    public StorageManager() {
        nestedTransaction = new HashMap<>();
        currentTransaction = new HashMap<>();
        currentNestedTransactionIndex = 0;
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
            nestedTransaction.put(currentNestedTransactionIndex, currentTransaction);
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
        currentTransaction = new HashMap<>();
        currentTransaction.putAll(nestedTransaction.get(currentNestedTransactionIndex));
        currentNestedTransactionIndex++;
        nestedTransaction.put(currentNestedTransactionIndex, currentTransaction);
    }

    @Override
    public void commitTransaction() {
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

    @Override
    public void abortTransaction() {
        if (currentNestedTransactionIndex == 0) {
            System.out.println("ERROR: Not within a transaction to abort.");
            return;
        }
        nestedTransaction.remove(currentNestedTransactionIndex);
        currentNestedTransactionIndex--;
        currentTransaction = new HashMap<>();
        currentTransaction.putAll(nestedTransaction.get(currentNestedTransactionIndex));

    }

    @Override
    public void deleteFromStorage(String input) {
        String key = input.split(SPLIT_STRING)[1];
        Map<String, String> keyValue = nestedTransaction.get(currentNestedTransactionIndex);
        if (keyValue != null) {
            keyValue.remove(key);
        } else {
            System.out.println("ERROR: Unable to delete, key does not exist.");
        }
    }
}
