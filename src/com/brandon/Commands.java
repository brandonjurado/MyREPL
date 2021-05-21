package com.brandon;

import java.util.HashMap;
import java.util.Map;

public enum Commands {
    READ("READ"),
    WRITE("WRITE"),
    DELETE("DELETE"),
    START("START"),
    COMMIT("COMMIT"),
    ABORT("ABORT"),
    QUIT("QUIT"),
    UNKNOWN("UNKNOWN");

    private final String command;
    private static final Map<String, Commands> reverseLookup;

    Commands(String command) {
        this.command = command;
    }

    static {
        reverseLookup = new HashMap<>();
        for (Commands enumValue : Commands.values()) {
            reverseLookup.put(enumValue.name(), enumValue);
        }
    }

    public static Commands ifContains(String line) {
        String[] command = line.split("\\s+");
        return reverseLookup.getOrDefault(command[0].toUpperCase(), UNKNOWN);
    }
}
