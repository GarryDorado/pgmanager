package com.supertel.pgmanager.process;

/**
 * File: ProcessReturnValues.java (UTF-8)
 * Date: Jan 19, 2018
 * Time: 7:48:23 AM
 */
public class ProcessReturnValues {

    private StringBuilder input = new StringBuilder();
    private StringBuilder error = new StringBuilder();

    public ProcessReturnValues() {
    }

    public void appendLineToInput(String s) {
        if (!s.isEmpty()) {
            input.append(s);
        }
    }

    public void appendLineToError(String s) {
        if (!s.isEmpty()) {
            error.append(s);
        }
    }

    public String getInput() {
        return input.toString();
    }

    public String getError() {
        return error.toString();
    }
}
