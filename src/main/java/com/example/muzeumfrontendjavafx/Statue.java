package com.example.muzeumfrontendjavafx;

public class Statue {
    private Integer id;
    private String person;
    private int height;
    private int price;

    public String toJson(){
        return "{\"person\":\"" + person + '\"' +
                ",\"height\":" + height  +
                ",\"price\":" + price +"}";
    }

    public Statue(Integer id, String person, int height, int price) {
        this.id = id;
        this.person = person;
        this.height = height;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPerson() {
        return person;
    }

    public void setPerson(String person) {
        this.person = person;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
