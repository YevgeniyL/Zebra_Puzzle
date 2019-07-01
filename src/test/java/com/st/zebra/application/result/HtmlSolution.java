package com.st.zebra.application.result;

import com.st.zebra.application.result.xml.House;
import com.st.zebra.application.result.xml.Solution;
import com.st.zebra.application.result.xml.Solutions;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.util.JAXBSource;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HtmlSolution {

    /**
     * Create html-file 'generated-page.html' for visual validate, how work XSLT from 'stylesheet.xsl'
     *
     * @throws TransformerException
     * @throws JAXBException
     * @throws IOException
     */
    @Test
    public void createHtml() throws TransformerException, JAXBException, IOException {
        List<Map<String, Map<String, String>>> resultList = buildResultList();
        Solutions result = convertToSolution(
                resultList
        );

        // Source
        JAXBContext jc = JAXBContext.newInstance(Solutions.class);
        JAXBSource source = new JAXBSource(jc, result);

        //Transformer
        Path path = Paths.get("stylesheet.xsl");
        StreamSource xslStreamSource = new StreamSource(Files.newInputStream(path));
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer(xslStreamSource);
        FileWriter writer = new FileWriter(new File("generated-page.html"));
        StreamResult out = new StreamResult(writer);
        transformer.transform(source, out);
    }

    /**
     * Create 'Solution' object and fill input data
     *
     * @param inputData - data with house parameters
     * @return filled 'Solutions'
     */
    private Solutions convertToSolution(List<Map<String, Map<String, String>>> inputData) {
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

    private List<Map<String, Map<String, String>>> buildResultList() {
        List<Map<String, Map<String, String>>> resultList = new ArrayList<>();
        HashMap<String, Map<String, String>> result = new HashMap<>();

        HashMap<String, String> map1 = new HashMap<>();
        map1.put(House.Fields.nationality.name(), "Norwegian");
        map1.put(House.Fields.color.name(), "Yellow");
        map1.put(House.Fields.smoke.name(), "Kools");
        map1.put(House.Fields.position.name(), "1");
        map1.put(House.Fields.pet.name(), "Fox");
        map1.put(House.Fields.drink.name(), "Water");

        HashMap<String, String> map2 = new HashMap<>();
        map2.put(House.Fields.nationality.name(), "Ukrainian");
        map2.put(House.Fields.color.name(), "Blue");
        map2.put(House.Fields.smoke.name(), "Chesterfields");
        map2.put(House.Fields.position.name(), "2");
        map2.put(House.Fields.pet.name(), "Horse");
        map2.put(House.Fields.drink.name(), "Tea");

        HashMap<String, String> map3 = new HashMap<>();
        map3.put(House.Fields.nationality.name(), "English");
        map3.put(House.Fields.color.name(), "Red");
        map3.put(House.Fields.smoke.name(), "Old gold");
        map3.put(House.Fields.position.name(), "3");
        map3.put(House.Fields.pet.name(), "Snails");
        map3.put(House.Fields.drink.name(), "Milk");

        HashMap<String, String> map4 = new HashMap<>();
        map4.put(House.Fields.nationality.name(), "Spaniard");
        map4.put(House.Fields.color.name(), "Ivory");
        map4.put(House.Fields.smoke.name(), "Lucky strike");
        map4.put(House.Fields.position.name(), "4");
        map4.put(House.Fields.pet.name(), "Dog");
        map4.put(House.Fields.drink.name(), "Orange juice");

        HashMap<String, String> map5 = new HashMap<>();
        map5.put(House.Fields.nationality.name(), "Japanese");
        map5.put(House.Fields.color.name(), "Green");
        map5.put(House.Fields.smoke.name(), "Parliaments");
        map5.put(House.Fields.position.name(), "5");
        map5.put(House.Fields.pet.name(), "Zebra");
        map5.put(House.Fields.drink.name(), "Coffee");

        result.put("1", map1);
        result.put("2", map2);
        result.put("3", map3);
        result.put("4", map4);
        result.put("5", map5);
        resultList.add(result);
        return resultList;
    }
}
