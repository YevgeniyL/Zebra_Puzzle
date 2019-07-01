package com.st.zebra.application.processors.rule;

/**
 * This one parameter that a must be in input 'rules'
 */
public enum Type {
    POSITION("position");
    private final String position;

    Type(String position) {
        this.position = position;
    }

    public String val() {
        return position;
    }
}
