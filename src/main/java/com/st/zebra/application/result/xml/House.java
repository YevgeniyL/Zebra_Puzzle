package com.st.zebra.application.result.xml;

import javax.xml.bind.annotation.XmlAttribute;

/**
 * Used in out xml structure
 */
public class House {
    @XmlAttribute
    private String position;
    @XmlAttribute
    private String color;
    @XmlAttribute
    private String nationality;
    @XmlAttribute
    private String drink;
    @XmlAttribute
    private String smoke;
    @XmlAttribute
    private String pet;

    public House(String position, String color, String nationality, String drink, String smoke, String pet) {
        this.position = position;
        this.color = color;
        this.nationality = nationality;
        this.drink = drink;
        this.smoke = smoke;
        this.pet = pet;
    }

    public enum Fields {
        position, color, nationality, drink, smoke, pet
    }
}
