package com.st.zebra;

import com.st.zebra.application.Processor;
import com.st.zebra.application.ResultConsumer;
import com.st.zebra.application.processors.ZebraProcessor;
import com.st.zebra.application.processors.rule.Rule;
import com.st.zebra.application.result.XmlSolution;
import com.st.zebra.application.utils.FilesUtil;
import com.st.zebra.application.utils.RuleUtil;
import com.st.zebra.infrastructure.AppException;

import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Application read 'input.txt' file and get all values as rules
 * Find solution by 'rules' and save list of 'result' it into file 'output.xml'
 */
public class Main {
    private final static Logger log = Logger.getLogger(XmlSolution.class.getName());

    public static void main(String[] args) {
        try {
            List<Rule> rules = new ArrayList<>();
            FilesUtil.getFileLines("input.txt").get().forEach(line -> rules.add(RuleUtil.createNew(line)));
            Processor processor = new ZebraProcessor();
            List<Map<String, Map<String, String>>> resultHouses = processor.run(rules);
            if (resultHouses.size() > 0) {
                ResultConsumer generator = new XmlSolution();
                String xmlText = generator.run(resultHouses);
                FilesUtil.createFileFromLines(Collections.singletonList(xmlText), "output.xml", StandardOpenOption.CREATE_NEW);
            } else {
                log.log(Level.INFO, "Solutions not founded");
            }

            log.log(Level.INFO, "End of work process");
        } catch (AppException | IllegalArgumentException e) {
            log.log(Level.WARNING, "Application stop work with error: " + e.getMessage());
        }
    }
}
