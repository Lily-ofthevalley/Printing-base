package com.example.printingbase;

import android.graphics.Bitmap;

public class ProjectModel {

    private int id;
    private String projectName;
    private  String filament;
    private int printTime;
    private boolean isPrinting;
    private boolean isFinished;
    private int filamentNeeded;
    private Bitmap projectImage;

    //constructor
    public ProjectModel(int id, String projectName, String filament, int printTime, int filamentNeeded){
        this.id = id;
        this.projectName = projectName;
        this.filament = filament;
        this.printTime = printTime;
        this.filamentNeeded = filamentNeeded;
        this.isPrinting = isPrinting;
        this.isFinished = isFinished;
    }

    public ProjectModel(int id, String projectName, String filament, int printTime, int filamentNeeded, Bitmap projectImage){
        this.id = id;
        this.projectName = projectName;
        this.filament = filament;
        this.printTime = printTime;
        this.filamentNeeded = filamentNeeded;
        this.isPrinting = isPrinting;
        this.isFinished = isFinished;
        this.projectImage = projectImage;
    }

    @Override
    public String toString(){
        return "Project name = " + projectName + "\n" +
                "Print time in minutes = " + printTime + "\n" +
                "Filament used = " + filament + "\n" +
                "Amount of filament needed = " + filamentNeeded + " grams";
    }

    //thingy
    public ProjectModel(){
    }

    //getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getFilament() {
        return filament;
    }

    public void setFilament(String filament) {
        this.filament = filament;
    }

    public int getPrintTime() {
        return printTime;
    }

    public void setPrintTime(int printTime) {
        this.printTime = printTime;
    }

    public boolean isPrinting() {
        return isPrinting;
    }

    public void setPrinting(boolean printing) {
        isPrinting = printing;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public int getFilamentNeeded() {
        return filamentNeeded;
    }

    public void setFilamentNeeded(int filamentNeeded) {
        this.filamentNeeded = filamentNeeded;
    }

    public Bitmap getProjectImage() {
        return projectImage;
    }

    public void setProjectImage(Bitmap projectImage) {
        this.projectImage = projectImage;
    }
}
