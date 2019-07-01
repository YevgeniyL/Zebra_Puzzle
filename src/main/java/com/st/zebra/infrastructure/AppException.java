package com.st.zebra.infrastructure;

/**
 * Custom exception for catch application exceptions and translate messages for user
 * Can be extended or modified for any reasons
 */
public class AppException extends Exception {

    public String message;

    public AppException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
