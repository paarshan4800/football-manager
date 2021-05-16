package com.models;

public class Manager {
    private int managerID;
    private String name;
    private String country;
    private int age;

    public Manager() {
    }

    public Manager(int managerID, String name, String country, int age) {
        this.managerID = managerID;
        this.name = name;
        this.country = country;
        this.age = age;
    }

    @Override
    public String toString() {
        return getName();
    }

    public int getManagerID() {
        return managerID;
    }

    public void setManagerID(int managerID) {
        this.managerID = managerID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}

