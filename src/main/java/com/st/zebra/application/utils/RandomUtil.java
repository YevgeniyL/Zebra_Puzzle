package com.st.zebra.application.utils;


import java.util.*;

/**
 * Class for work with random data
 */
public class RandomUtil {

    /**
     * Create all available values from 'input' list of values to 'output' list
     *
     * @param input   list of variables
     * @param output  contain ArrayList of Lists with unique values
     * @param zeroPos init element, by default set it = 0
     */
    public static void randomizeArray(List<String> input, List<List<String>> output, int zeroPos) {
        for (int i = zeroPos; i < input.size(); i++) {
            Collections.swap(input, i, zeroPos);
            randomizeArray(input, output, zeroPos + 1);
            Collections.swap(input, zeroPos, i);
        }
        if (zeroPos == input.size() - 1) {
            output.add(new ArrayList<>(input));
        }
    }

    /**
     * Clone all values from temp-map('result') to out map 'result'
     *
     * @param result temp result map for bruteForce
     * @return new map with cloned values
     */
    public static Map<String, Map<String, String>> cloneResult(Map<String, Map<String, String>> result) {
        Map<String, Map<String, String>> out = new HashMap<>();
        for (Map.Entry<String, Map<String, String>> numberWithHousesParam : result.entrySet()) {
            String positionNumber = numberWithHousesParam.getKey();
            out.put(positionNumber, new HashMap<>());
            for (Map.Entry<String, String> housesParam : numberWithHousesParam.getValue().entrySet()) {
                out.get(positionNumber).put(housesParam.getKey(), housesParam.getValue());
            }
        }

        return out;
    }
}
