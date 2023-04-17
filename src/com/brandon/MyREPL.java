package com.brandon;

public interface MyREPL {

    void storeKeyValue(String input);
    void readStorage(String input);
    void startTransaction();
    void commitTransaction();
    void abortTransaction();
    void deleteFromStorage(String input);
}
