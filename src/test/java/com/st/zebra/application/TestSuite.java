package com.st.zebra.application;

import com.st.zebra.application.processors.ZebraProcessorTest;
import com.st.zebra.application.result.HtmlSolution;
import com.st.zebra.application.result.XmlSolutionGeneratorTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ZebraProcessorTest.class,
        XmlSolutionGeneratorTest.class,
        HtmlSolution.class
})

public class TestSuite {
}
