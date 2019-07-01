package com.st.zebra.application.result;

import com.st.zebra.application.result.xml.House;
import com.st.zebra.infrastructure.AppException;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XmlSolutionGeneratorTest {

    /**
     * Check, that data from resultList will be in xml
     *
     * @throws AppException
     */
    @Test
    public void generalTest() throws AppException {
        String expectedXml = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n" +
                "<solutions>\n" +
                "    <solution>\n" +
                "        <house position=\"1\" color=\"Red\" nationality=\"English\" drink=\"Water\" smoke=\"Kools\" pet=\"Dog\"/>\n" +
                "        <house position=\"2\" color=\"Yellow\" nationality=\"Ukrainian\" drink=\"Tea\" smoke=\"Chesterfields\" pet=\"Zebra\"/>\n" +
                "        <house position=\"3\" color=\"Green\" nationality=\"Spaniard\" drink=\"Milk\" smoke=\"Old gold\" pet=\"Fox\"/>\n" +
                "    </solution>\n" +
                "</solutions>\n";

        String resultXml = new XmlSolution().run(buildResultList());
        Assert.assertEquals(expectedXml, resultXml);
    }

    private List<Map<String, Map<String, String>>> buildResultList() {
        List<Map<String, Map<String, String>>> resultList = new ArrayList<>();
        HashMap<String, Map<String, String>> result = new HashMap<>();
        HashMap<String, String> map1 = new HashMap<>();
        map1.put(House.Fields.nationality.name(), "English");
        map1.put(House.Fields.color.name(), "Red");
        map1.put(House.Fields.smoke.name(), "Kools");
        map1.put(House.Fields.position.name(), "1");
        map1.put(House.Fields.pet.name(), "Dog");
        map1.put(House.Fields.drink.name(), "Water");

        HashMap<String, String> map2 = new HashMap<>();
        map2.put(House.Fields.nationality.name(), "Ukrainian");
        map2.put(House.Fields.color.name(), "Yellow");
        map2.put(House.Fields.smoke.name(), "Chesterfields");
        map2.put(House.Fields.position.name(), "2");
        map2.put(House.Fields.pet.name(), "Zebra");
        map2.put(House.Fields.drink.name(), "Tea");

        HashMap<String, String> map3 = new HashMap<>();
        map3.put(House.Fields.nationality.name(), "Spaniard");
        map3.put(House.Fields.color.name(), "Green");
        map3.put(House.Fields.smoke.name(), "Old gold");
        map3.put(House.Fields.position.name(), "3");
        map3.put(House.Fields.pet.name(), "Fox");
        map3.put(House.Fields.drink.name(), "Milk");
        result.put("1", map1);
        result.put("2", map2);
        result.put("3", map3);
        resultList.add(result);
        return resultList;
    }
}