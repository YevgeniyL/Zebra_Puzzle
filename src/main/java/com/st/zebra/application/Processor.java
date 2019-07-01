package com.st.zebra.application;


import com.st.zebra.application.processors.rule.Rule;
import com.st.zebra.infrastructure.AppException;

import java.util.List;
import java.util.Map;

/**
 * Interface for solve problem by input 'sourceRules'
 */
public interface Processor {

    /**
     * Start solve puzzle by input data 'sourceRules'
     *
     * @param sourceRules list with rules
     * @return result map
     * @throws AppException
     */
    List<Map<String, Map<String, String>>> run(List<Rule> sourceRules) throws AppException;
}
