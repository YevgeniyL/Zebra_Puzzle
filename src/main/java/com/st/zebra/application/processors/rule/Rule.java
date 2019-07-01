package com.st.zebra.application.processors.rule;

/**
 * Class for hold input data as 'rule'
 */
public class Rule {
    private Direction direction;
    private String firstPredicate;
    private String firstPredicatesText;
    private String secondPredicate;
    private String secondPredicatesText;

    public Rule(Direction direction, String firstPredicate, String firstPredicatesText, String secondPredicate, String secondPredicatesText) {
        this.direction = direction;
        this.firstPredicate = firstPredicate;
        this.firstPredicatesText = firstPredicatesText;
        this.secondPredicate = secondPredicate;
        this.secondPredicatesText = secondPredicatesText;
    }

    public Direction getDirection() {
        return direction;
    }

    public String getFirstPredicate() {
        return firstPredicate;
    }

    public String getFirstPredicatesText() {
        return firstPredicatesText;
    }

    public String getSecondPredicate() {
        return secondPredicate;
    }

    public String getSecondPredicatesText() {
        return secondPredicatesText;
    }
}
