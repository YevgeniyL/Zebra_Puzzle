package com.st.zebra.application.utils;

import com.st.zebra.application.processors.rule.Direction;
import com.st.zebra.application.processors.rule.Rule;
import com.st.zebra.application.processors.rule.Type;
import com.st.zebra.infrastructure.AppException;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Helper to work with Rules
 */
public class RuleUtil {
    private final static Logger log = Logger.getLogger(RuleUtil.class.getName());

    /**
     * Create new valid Rule
     * Validate input string
     * Set Direction of processors, rules text and fill TypeList
     *
     * @param inputString text with values, separated ';'. Text looks like 'SAME;nationality;Norwegian;position;1'
     * @return new Rule object
     * @throws IllegalArgumentException if input data is not valid
     */
    public static Rule createNew(String inputString) throws IllegalArgumentException {
        if (inputString == null || inputString.isEmpty())
            throw new IllegalArgumentException("Empty line");

        String[] data = inputString.split(";");
        if (data.length > 5 || data.length < 3)
            throw new IllegalArgumentException("Rule '" + inputString + "' has wrong structure");

        boolean ruleIsNullable = Stream.of(data).anyMatch(Objects::isNull);
        if (ruleIsNullable)
            throw new IllegalArgumentException("One of param in rule '" + inputString + "' + is empty");

        String firstElement = data[0];
        if (firstElement.isEmpty())
            throw new IllegalArgumentException("Empty direction in rule '" + inputString + "'");

        Direction direction = Direction.valueOf(firstElement);
        String firstType = data[1];
        String firstTypeText = data[2];
        if (data.length > 3) {
            String secondType = data[3];
            String secondTypeText = data[4];
            return new Rule(direction, firstType, firstTypeText, secondType, secondTypeText);
        }

        return new Rule(direction, firstType, firstTypeText, null, null);
    }

    /**
     * Build map of type names with all founded values for this type from input data
     *
     * @param sourceRules original processors from string
     * @return map like:
     * @throws AppException <'nationality', <'English','Ukrainian'...'Japanese'>>
     *                      <'color', <'Red','Ivory'...'Yellow'>>
     */
    public static Map<String, Set<String>> buildTypesMapWithDataFrom(List<Rule> sourceRules) {
        Map<String, Set<String>> typeMapOfRules = new HashMap<>();
        sourceRules.forEach(
                rule -> {
                    putValuesFromRuleTo(rule.getFirstPredicate(), rule.getFirstPredicatesText(), typeMapOfRules);
                    putValuesFromRuleTo(rule.getSecondPredicate(), rule.getSecondPredicatesText(), typeMapOfRules);
                });

        return typeMapOfRules;
    }

    /**
     * Validate input data for missing or duplicate parameters.
     * 'position' param will be excluded. We are know, that it will to be from 1.. to x
     *
     * @param typeMapOfRules validated data
     * @throws AppException
     */
    public static void validateDataCorrection(Map<String, Set<String>> typeMapOfRules) throws AppException {
        try {

            int min = Integer.MAX_VALUE;
            int max = 0;
            for (Map.Entry<String, Set<String>> type : typeMapOfRules.entrySet()) {
                if (type.getKey().equals(Type.POSITION.val()))
                    continue;
                max = type.getValue().size() > max ? type.getValue().size() : max;
                min = type.getValue().size() < min ? type.getValue().size() : min;
            }
            if (min != max) {
                String message = "Solution will not be founded, input data is not full. Count of all unique params mus be = "
                        + max + ", but one of all params contain " + min + " count (exclude 'position' - param)";
                log.log(Level.WARNING, message);
                throw new AppException(message);
            }

        } catch (NoSuchElementException e) {
            AppException ex = new AppException("Wrong input data");
            log.log(Level.WARNING, ex.getMessage(), e);
            throw ex;
        }
    }

    /**
     * Build map from input rules like:
     * key = "nationality"
     * value =
     * 0 = "English"
     * 1 = "Ukrainian"
     * 2 = "Spaniard"
     *
     * @param typeName       key in result map
     * @param typeValue      value in result by key
     * @param typeMapOfRules filled map
     */
    private static void putValuesFromRuleTo(String typeName, String typeValue, Map<String, Set<String>> typeMapOfRules) {
        if (typeName != null && typeValue != null) {
            if (typeMapOfRules.containsKey(typeName)) {
                typeMapOfRules.get(typeName).add(typeValue);
            } else {
                typeMapOfRules.put(typeName, new HashSet<>(Collections.singletonList(typeValue)));
            }
        }
    }

    /**
     * Exclude rules without any value.
     * Because that rules not have full data for correct validation
     *
     * @param sourceRules list of rules
     * @return list of working rules
     */
    public static List<Rule> cleanRules(List<Rule> sourceRules) {
        return sourceRules.stream()
                .filter(r -> r.getDirection() != null
                        && r.getFirstPredicate() != null && r.getFirstPredicatesText() != null
                        && r.getSecondPredicate() != null && r.getSecondPredicatesText() != null)
                .collect(Collectors.toList());
    }
}
