package com.st.zebra.application;


import com.st.zebra.infrastructure.AppException;

import java.util.List;
import java.util.Map;

/**
 * Build result from data
 */
public interface ResultConsumer {

    /**
     * Get 'resultHouses'
     *
     * @param resultHouses input source data
     * @return result as string
     * @throws AppException
     */
    String run(List<Map<String, Map<String, String>>> resultHouses) throws AppException;
}
