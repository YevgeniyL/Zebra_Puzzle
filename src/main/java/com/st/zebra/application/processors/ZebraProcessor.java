package com.st.zebra.application.processors;

import com.st.zebra.application.Processor;
import com.st.zebra.application.processors.rule.Rule;
import com.st.zebra.application.processors.rule.Type;
import com.st.zebra.application.utils.RandomUtil;
import com.st.zebra.application.utils.RuleUtil;
import com.st.zebra.infrastructure.AppException;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Base class for finding solution for Zebra-puzzle
 */
public class ZebraProcessor implements Processor {
    private final static Logger log = Logger.getLogger(ZebraProcessor.class.getName());

    /**
     * Outgoing list all founded solutions
     */
    private List<Map<String, Map<String, String>>> resultList = new ArrayList<>();

    /**
     * count of incoming types(house description params) from 'sourceRules'
     */
    private int countAllTypes = 0;

    /**
     * Create result from input data 'sourceRules'
     * Build random data, bruteForce it and every value comparing with all from 'sourceRules'
     *
     * @param sourceRules list with rules
     * @return filled map of house numbers with valid house description
     * @throws AppException
     */
    public List<Map<String, Map<String, String>>> run(List<Rule> sourceRules) throws AppException {
        final Map<String, Set<String>> inputHousesData = RuleUtil.buildTypesMapWithDataFrom(sourceRules);
        RuleUtil.validateDataCorrection(inputHousesData);
        final List<Rule> cleanedRules = RuleUtil.cleanRules(sourceRules);
        final Set<String> housesNumbersSet = IntStream
                .rangeClosed(1, getHousesCount(inputHousesData)).boxed()
                .map(String::valueOf)
                .collect(Collectors.toSet());

        countAllTypes = inputHousesData.size();
        final Map<String, List<List<String>>> bigRandomData = buildCombinations(inputHousesData, housesNumbersSet);
        Map<String, Map<String, String>> result = buildResultWithDefaultData(inputHousesData, housesNumbersSet);
        loopBruteForce(
                bigRandomData, cleanedRules, result
        );

        return resultList;
    }

    /**
     * Calculate houses count from 'inputHousesData'
     *
     * @param inputHousesData - map of type names with all founded values (house params) for this type from input data
     * @return count of houses
     */
    private int getHousesCount(Map<String, Set<String>> inputHousesData) {
        int housesCount = 0;
        for (Set<String> value : inputHousesData.values()) {
            if (value.size() > housesCount) {
                housesCount = value.size();
            }
        }
        return housesCount;
    }

    /**
     * General process with bruteForce data and validateNext result.
     * Return 'true' after processing if found all good combination.
     * Step '1': In loopBruteForce get every random values in type, add it into the 'result' and check 'result' for valid set.
     * Step '2': If values is valid - get a new type with random values and do step '1' for this type
     *
     * @param bigRandomData - map of params with all available combinations for 'bruteForce'
     * @param rules         - rules for check bruteForced combination
     * @param result        - map-collector for result data
     * @return boolean - if bruteForcing process is finished return 'true'
     */
    private boolean loopBruteForce(Map<String, List<List<String>>> bigRandomData, List<Rule> rules, Map<String, Map<String, String>> result) {
        Set<String> typesInResult = new HashSet<>(result.values().stream().findFirst().get().keySet());
        String typeName = bigRandomData.keySet().stream().filter(random -> !typesInResult.contains(random)).findFirst().get();
        List<List<String>> randomValues = bigRandomData.get(typeName);
        for (List<String> valuesToTest : randomValues) {
            addToResult(result, typeName, valuesToTest);
            int typesCountInResult = result.values().stream().findFirst().get().size();

            //Validate all values in result by all rules
            boolean isValidSet = checkForValid(
                    result, rules
            );

            if (isValidSet && countAllTypes == typesCountInResult) {
                resultList.add(new HashMap<>(RandomUtil.cloneResult(result)));
            }

            if (isValidSet && countAllTypes != typesCountInResult) {
                loopBruteForce(
                        bigRandomData, rules, result
                );
            }

            removeFromResult(result, typeName);
        }

        return true;
    }

    /**
     * From every house remove type (param or description) with value
     *
     * @param result          - from this map remove type
     * @param removedTypeName removed typeName
     */
    private void removeFromResult(Map<String, Map<String, String>> result, String removedTypeName) {
        for (Map<String, String> houseData : result.values()) {
            houseData.remove(removedTypeName);
        }
    }

    /**
     * Insert into result type name with values.
     *
     * @param result        result map
     * @param addedTypeName added type name
     * @param addedValues   added values fof type
     */
    private void addToResult(Map<String, Map<String, String>> result, String addedTypeName, List<String> addedValues) {
        for (int i = 1; i < result.values().size() + 1; i++) {
            Map<String, String> houseData = result.get(String.valueOf(i));
            houseData.put(addedTypeName, addedValues.get(i - 1));
        }
    }

    /**
     * Create a map for collect valid data (result)
     * 'Positions' have a fixed order and count - it is default data, therefore put it into result
     *
     * @param inputData    - map of params with unique values like <nationality, <English,Ukrainian,..>>
     * @param houseNumbers - houses numbers (1,2,3,4,...)
     * @return map-collector for result data like:
     * <1,<position,1>>
     * <1,<pet,Dog>>
     * ......
     * <2,<position,2>>
     * <2,<drink,Water>>
     * ......
     */
    private Map<String, Map<String, String>> buildResultWithDefaultData(Map<String, Set<String>> inputData, Set<String> houseNumbers) {
        if (!inputData.containsKey(Type.POSITION.val())) {
            countAllTypes++;
        }

        inputData.remove(Type.POSITION.val());
        inputData.put(Type.POSITION.val(), new HashSet<>(houseNumbers));
        Map<String, Map<String, String>> result = new HashMap<>();
        for (String positionNum : inputData.get(Type.POSITION.val())) {
            result.put(positionNum, new HashMap<>());
            result.get(positionNum).put(Type.POSITION.val(), positionNum);
        }
        return result;
    }

    /**
     * General method for data validation
     * By every 'processors' will be check 'result' data.
     *
     * @param result validated result-data
     * @param rules  validation rules
     * @return true if all values valid.
     */
    private boolean checkForValid(Map<String, Map<String, String>> result, List<Rule> rules) {
        boolean resultIsValid = false;
        boolean hasFail = false;

        final Set<String> typeList = result.values().stream()
                .flatMap(values -> values.keySet().stream())
                .collect(Collectors.toSet());

        for (Rule rule : rules) {
            if (ruleIsNotActualAtThisStageFor(typeList, rule))
                continue;

            if (hasFail) {
                break;
            }

            //This 'processors' 100% need be a passed, else data in 'result' - is a fake
            boolean isPassedRule = false;
            final String firstPredicate = rule.getFirstPredicate();
            final String firstPredicatesText = rule.getFirstPredicatesText();
            final String secondPredicate = rule.getSecondPredicate();
            final String secondPredicatesText = rule.getSecondPredicatesText();
            switch (rule.getDirection()) {
                case SAME:
                    //One of houses must be valid for processors, else processors is not passed
                    for (Map.Entry<String, Map<String, String>> houseEntry : result.entrySet()) {
                        final Map<String, String> house = houseEntry.getValue();
                        final String firsParam = house.get(firstPredicate);
                        final String secondParam = house.get(secondPredicate);
                        if (firsParam.equals(firstPredicatesText) && secondParam.equals(secondPredicatesText)) {
                            isPassedRule = true;
                            break;
                        }
                    }

                    if (!isPassedRule)
                        hasFail = true;

                    break;
                case TO_THE_LEFT_OF:
                    //Left house must contain FIRST predicate in 'processors', rightHouse - must contain SECOND processors. Lets will find left house by 'processors'
                    hasFail = hasFail(result, firstPredicate, firstPredicatesText, secondPredicate, secondPredicatesText);
                    break;
                case NEXT_TO:
                    //Swap predicates, to simulate direction = 'TO_THE_LEFT_OF' and do all from 'TO_THE_LEFT_OF'
                    hasFail = hasFail(result, secondPredicate, secondPredicatesText, firstPredicate, firstPredicatesText);
                    break;
            }
        }

        if (!hasFail)
            resultIsValid = true;

        return resultIsValid;
    }

    /**
     * Find first valid 'house' by predicate1 and check right 'house' data by predicate2
     *
     * @param result         - to validate map-collector result data
     * @param predicate1     - left house type
     * @param predicate1Text - left house data
     * @param predicate2     - right house type
     * @param predicate2Text - right house data
     * @return true if validation failed
     */
    private boolean hasFail(Map<String, Map<String, String>> result, String predicate1, String predicate1Text, String predicate2, String predicate2Text) {
        final Map<String, String> leftHouse = result.values().stream()
                .filter(h -> h.containsKey(predicate1) && h.get(predicate1).equals(predicate1Text))
                .findFirst()
                .orElse(null);

        if (leftHouse == null) {
            return true;
        }

        //Left house can not be a last
        final String leftHousePosition = leftHouse.get(Type.POSITION.val());
        if (Integer.valueOf(leftHousePosition).equals(result.size())) {
            return true;
        }

        final String rightHousePosition = String.valueOf(Integer.valueOf(leftHousePosition) + 1);
        final Map<String, String> rightHouse = result.get(rightHousePosition);
        final String leftParam = leftHouse.get(predicate1);
        final String rightParam = rightHouse.get(predicate2);
        return !leftParam.equals(predicate1Text) || !rightParam.equals(predicate2Text);
    }

    /**
     * If 'checkedType' yet not contain information for this processors will need to skip this processors and return true
     * else false
     *
     * @param checkedType - checked data
     * @param rule        - processors with validation data
     * @return true or false
     */
    private boolean ruleIsNotActualAtThisStageFor(Set<String> checkedType, Rule rule) {
        if (!checkedType.contains(rule.getFirstPredicate()))
            return true;

        return !checkedType.contains(rule.getSecondPredicate());

    }

    /**
     * Build all combinations for every house param(type) from input data.
     * Type 'position' - have a fixed order (1,2,3,4,...) and not be mixed, but added to combinations
     *
     * @param inputHousesData - map of params with unique values like <nationality, <English,Ukrainian,..>>
     * @param houseNumbers    - houses numbers (1,2,3,4,...)
     * @return map of params with all available combinations for 'bruteForce'
     */
    private Map<String, List<List<String>>> buildCombinations(Map<String, Set<String>> inputHousesData, Set<String> houseNumbers) {
        Map<String, List<List<String>>> bruteForceData = new HashMap<>();
        for (Map.Entry<String, Set<String>> entry : inputHousesData.entrySet()) {
            if (entry.getKey().equals(Type.POSITION.val())) {
                bruteForceData.put(Type.POSITION.val(), Collections.singletonList(new ArrayList<>(houseNumbers)));
                continue;
            }

            List<List<String>> randomizedValues = new ArrayList<>();
            RandomUtil.randomizeArray(new ArrayList<>(entry.getValue()), randomizedValues, 0);
            bruteForceData.put(entry.getKey(), randomizedValues);
        }

        return bruteForceData;
    }
}
