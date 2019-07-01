package com.st.zebra.application.processors;

import com.st.zebra.application.processors.rule.Rule;
import com.st.zebra.application.utils.RuleUtil;
import com.st.zebra.infrastructure.AppException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ZebraProcessorTest
 */
public class ZebraProcessorTest {

    /**
     * Test 'classic' puzzle. Check Zebra and Water positions
     *
     * @throws AppException
     */
    @Test
    public void validateClassicDataTest() throws AppException {
        List<Rule> rules = buildClassicValidData();
        List<Map<String, Map<String, String>>> resultList = new ZebraProcessor().run(rules);

        Assert.assertNotNull(resultList);
        Assert.assertEquals(1, resultList.size());
        Map<String, String> firstHouse = resultList.get(0).get("1");
        Assert.assertNotNull(firstHouse.get("drink"));
        Assert.assertEquals("Water", firstHouse.get("drink"));
        Map<String, String> lastHouse = resultList.get(0).get("5");
        Assert.assertNotNull(lastHouse.get("pet"));
        Assert.assertEquals("Zebra", lastHouse.get("pet"));
    }

    /**
     * Test 'small' puzzle.
     * Solutions count = 6
     *
     * @throws AppException
     */
    @Test
    public void validateMultiSolution() throws AppException {
        List<Rule> rules = buildValid6SmallSolutions();
        List<Map<String, Map<String, String>>> resultList = new ZebraProcessor().run(rules);

        Assert.assertNotNull(resultList);
        Assert.assertEquals(6, resultList.size());

        //compare all founded maps
        //Check that all results in list is a unique
        for (Map<String, Map<String, String>> map : resultList) {
            int counter = 0;
            for (Map<String, Map<String, String>> source : resultList) {
                if (map.equals(source))
                    counter++;
            }
            Assert.assertEquals(1, counter);
        }
    }

    /**
     * Check missed param.
     * <p>
     * Solution will not be founded, input data is not full.
     * Count of all unique params mus be = 5, but one of all params contain 4 count (exclude 'position' - param)
     *
     * @throws AppException
     */
    @Test(expected = AppException.class)
    public void catchException1() throws AppException {
        List<Rule> rules = buildClassicValidData();
        rules.remove(0);
        new ZebraProcessor().run(rules);
    }

    /**
     * Add fake param in rules
     * <p>
     * Solution will not be founded, input data is not full. Count of all unique params mus be = 6, but one of all params contain 5 count (exclude 'position' - param)
     *
     * @throws AppException
     */
    @Test(expected = AppException.class)
    public void catchException2() throws AppException {
        List<Rule> rules = buildClassicValidData();
        rules.add(RuleUtil.createNew("SAME;nationality;English1;color;Red"));
        new ZebraProcessor().run(rules);
    }

    /**
     * miss one param in rule
     *
     * @throws AppException
     */
    @Test(expected = IllegalArgumentException.class)
    public void catchException3() throws AppException {
        List<Rule> rules = buildClassicValidData();
        rules.add(RuleUtil.createNew("SAME;nationality;;"));
        new ZebraProcessor().run(rules);
    }

    /**
     * Add unreal rule
     *
     * @throws AppException
     */
    @Test(expected = IllegalArgumentException.class)
    public void catchException4() throws AppException {
        List<Rule> rules = buildClassicValidData();
        rules.add(RuleUtil.createNew("SAME;nationality;nationality;nationality;nationality;nationality"));
        new ZebraProcessor().run(rules);
    }

    /**
     * Collect data for 'classic' puzzle
     * Has 1 solution
     *
     * @return
     */
    private List<Rule> buildClassicValidData() {
        List<Rule> rules = new ArrayList<>();
        rules.add(RuleUtil.createNew("SAME;nationality;English;color;Red"));
        rules.add(RuleUtil.createNew("SAME;nationality;Spaniard;pet;Dog"));
        rules.add(RuleUtil.createNew("SAME;drink;Coffee;color;Green"));
        rules.add(RuleUtil.createNew("SAME;drink;Tea;nationality;Ukrainian"));
        rules.add(RuleUtil.createNew("TO_THE_LEFT_OF;color;Ivory;color;Green"));
        rules.add(RuleUtil.createNew("SAME;smoke;Old gold;pet;Snails"));
        rules.add(RuleUtil.createNew("SAME;smoke;Kools;color;Yellow"));
        rules.add(RuleUtil.createNew("SAME;drink;Milk;position;3"));
        rules.add(RuleUtil.createNew("SAME;nationality;Norwegian;position;1"));
        rules.add(RuleUtil.createNew("NEXT_TO;smoke;Chesterfields;pet;Fox"));
        rules.add(RuleUtil.createNew("TO_THE_LEFT_OF;smoke;Kools;pet;Horse"));
        rules.add(RuleUtil.createNew("SAME;smoke;Lucky strike;drink;Orange juice"));
        rules.add(RuleUtil.createNew("SAME;smoke;Parliaments;nationality;Japanese"));
        rules.add(RuleUtil.createNew("NEXT_TO;color;Blue;nationality;Norwegian"));
        rules.add(RuleUtil.createNew("SAME;drink;Water"));
        rules.add(RuleUtil.createNew("SAME;pet;Zebra"));
        return rules;
    }

    /**
     * Data for 'small' puzzle
     * Has 6 solutions
     *
     * @return
     */
    private List<Rule> buildValid6SmallSolutions() {
        List<Rule> rules = new ArrayList<>();
        rules.add(RuleUtil.createNew("SAME;nationality;English;pet;Dog"));
        rules.add(RuleUtil.createNew("SAME;nationality;Ukrainian;pet;Zebra"));
        rules.add(RuleUtil.createNew("SAME;nationality;Spaniard;pet;Fox"));
        rules.add(RuleUtil.createNew("SAME;nationality;English;drink;Tea"));
        rules.add(RuleUtil.createNew("SAME;nationality;Ukrainian;drink;Coffee"));
        rules.add(RuleUtil.createNew("SAME;nationality;Spaniard;drink;Water"));
        return rules;
    }
}