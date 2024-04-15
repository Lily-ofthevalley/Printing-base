package com.example.printingbase;

public class FilamentModel {

    private int id;
    private String filamentName;
    private String filamentBrand;
    private String filamentType;
    private String color;
    private int filamentAmount;

    //constructor
    public FilamentModel(int id, String filamentName, String filamentBrand, String filamentType, String color, int filamentAmount){
        this.id = id;
        this.filamentName = filamentName;
        this.filamentBrand = filamentBrand;
        this.filamentType = filamentType;
        this.color = color;
        this.filamentAmount = filamentAmount;
    }

    //toString is necessary for printing the contents of a class object
    @Override
    public String toString() {
        return "FilamentModel{" +
                "id=" + id +
                ", filamentName='" + filamentName + '\'' +
                ", filamentBrand='" + filamentBrand + '\'' +
                ", filamentType='" + filamentType + '\'' +
                ", color='" + color + '\'' +
                ", filamentAmount=" + filamentAmount +
                '}';
    }

    //thingy
    public FilamentModel(){
    }

    //getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilamentName() {
        return filamentName;
    }

    public void setFilamentName(String filamentName) {
        this.filamentName = filamentName;
    }

    public String getFilamentBrand() {
        return filamentBrand;
    }

    public void setFilamentBrand(String filamentBrand) {
        this.filamentBrand = filamentBrand;
    }

    public String getFilamentType() {
        return filamentType;
    }

    public void setFilamentType(String filamentType) {
        this.filamentType = filamentType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getFilamentAmount() {
        return filamentAmount;
    }

    public void setFilamentAmount(int filamentAmount) {
        this.filamentAmount = filamentAmount;
    }
}