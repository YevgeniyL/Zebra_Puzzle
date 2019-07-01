package com.st.zebra.application.result;

import com.st.zebra.application.ResultConsumer;
import com.st.zebra.application.result.xml.House;
import com.st.zebra.application.result.xml.Solution;
import com.st.zebra.application.result.xml.Solutions;
import com.st.zebra.infrastructure.AppException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Converter for perform input data to xml 'Solutions'-structure
 */
public class XmlSolution implements ResultConsumer {
    private final static Logger log = Logger.getLogger(XmlSolution.class.getName());

    /**
     * Use JAXB to convert Object into a string with xml structure like:
     * <solutions>
     * ***<solution>
     * ****<house position="1" nationality="English" drink="Tea" pet="Dog"/>
     * ****<house ***********************\/>
     * ****<house position="x" nationality="x" drink="x" pet="x"/>
     * ***</solution>
     * </solutions>
     *
     * @param inputData data to convert into string xml
     * @return string with xml structure
     */
    public String run(List<Map<String, Map<String, String>>> inputData) throws AppException {
        if (inputData == null || inputData.isEmpty())
            throw new AppException("Empty input data to create xml-string");

        Solutions result = buildResult(
                inputData
        );

        try {
            JAXBContext context = JAXBContext.newInstance(Solutions.class);
            Marshaller m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
            m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            StringWriter sw = new StringWriter();
            m.marshal(result, sw);
            return sw.toString();
        } catch (JAXBException e) {
            log.log(Level.WARNING, "Error in creating xml.", e);
            throw new AppException("Error in creating xml.");
        }
    }

    /**
     * Create 'Solution' object and fill input data
     *
     * @param inputData - data with house parameters
     * @return filled 'Solutions'
     */
    private Solutions buildResult(List<Map<String, Map<String, String>>> inputData) {
        Solutions result = new Solutions();
        ArrayList<Solution> solutions = new ArrayList<>();
        result.setSolutionList(solutions);
        for (Map<String, Map<String, String>> item : inputData) {
            Solution solution = new Solution();
            solutions.add(solution);
            ArrayList<House> houses = new ArrayList<>();
            solution.setHouse(houses);
            item.values().forEach(value -> {
                House house = new House(
                        value.get(House.Fields.position.name()),
                        value.get(House.Fields.color.name()),
                        value.get(House.Fields.nationality.name()),
                        value.get(House.Fields.drink.name()),
                        value.get(House.Fields.smoke.name()),
                        value.get(House.Fields.pet.name()));
                houses.add(house);
            });
        }

        return result;
    }
}
