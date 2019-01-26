package com.alcmag.rjmk.filemgmt2.Model;

public class wishes {
    private String name;
    private String date;
    private int id;
    private String secondwish;

    public wishes(){

    }

    public wishes(String name, String date, int id, String secondwish) {
        this.name = name;
        this.date = date;
        this.id = id;
        this.secondwish = secondwish;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSecondwish() {
        return secondwish;
    }

    public void setSecondwish(String secondwish) {
        this.secondwish = secondwish;
    }
}
