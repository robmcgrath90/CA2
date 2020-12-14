package com.example.ca2android;

public class ClientModel {


    //reference https://www.youtube.com/watch?v=hDSVInZ2JCs&fbclid=IwAR2YB6VBte9Peo-XCJhihZV575MH8U5w2NfEOjRVx9I12Ktj90HhFLQC_h4
    //adding variables to the class ClientModel
    private int id;
    private String name;
    private int weight;
    private boolean isActive;



    //constructors
    public ClientModel(int id, String name, int weight, boolean isActive) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.isActive = isActive;
    }

    //toString is necessary for printing the contents of a class object


    @Override
    public String toString() {
        return "ClientModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weight=" + weight+
                ", isActive=" + isActive +
                '}';
    }

    //non parameter constructor
    public ClientModel() {
    }

    //Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public  int getWeight() {
        return weight;
    }

    public void setWeight(int age) {
        this.weight = weight;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }




}
