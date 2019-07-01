package com.st.zebra.application.result.xml;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

/**
 * Used in out xml structure
 */
@XmlRootElement(name = "solutions")
public class Solutions {

    private List<Solution> solutionList;

    public Solutions() {
    }

    public List<Solution> getSolutionList() {
        return solutionList;
    }

    @XmlElement(name = "solution")
    public void setSolutionList(List<Solution> solutionList) {
        this.solutionList = solutionList;
    }
}
