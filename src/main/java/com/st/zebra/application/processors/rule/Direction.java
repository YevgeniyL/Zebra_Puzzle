package com.st.zebra.application.processors.rule;

/**
 * Direction of elements position in Zebra puzzle
 * Contain left, right or same values.
 * Original puzzle use neighborhood instead of 'left' and 'right'
 */
public enum Direction {
    SAME,
    TO_THE_LEFT_OF,
    NEXT_TO
}
