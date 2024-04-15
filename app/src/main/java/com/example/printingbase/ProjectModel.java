package com.example.printingbase;

public class ProjectModel {

    private int id;

    private String projectName;

    private  String filament;

    private int printTime;

    private boolean isPrinting;

    private boolean isFinished;

    //constructor
    public ProjectModel(int id, String projectName, String filament, int printTime, boolean isPrinting, boolean isFinished){
        this.id = id;
        this.projectName = projectName;
        this.filament = filament;
        this.printTime = printTime;
        this.isPrinting = isPrinting;
        this.isFinished = isFinished;
    }

    @Override
    public String toString(){
        return "ProjectModel{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", filament=" + filament +
                ", printTime=" + printTime +
                ", isPrinting=" + isPrinting +
                ", isFinished=" + isFinished +
                '}';
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
}
