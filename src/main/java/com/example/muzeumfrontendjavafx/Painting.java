package com.example.muzeumfrontendjavafx;

public class Painting {
    private Integer id;
    private String title;
    private int year;
    private boolean onDisplay;

    public Painting(String title, int year, boolean onDisplay) {
        this.title = title;
        this.year = year;
        this.onDisplay = onDisplay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public boolean isOnDisplay() {
        return onDisplay;
    }

    public void setOnDisplay(boolean onDisplay) {
        this.onDisplay = onDisplay;
    }
}
