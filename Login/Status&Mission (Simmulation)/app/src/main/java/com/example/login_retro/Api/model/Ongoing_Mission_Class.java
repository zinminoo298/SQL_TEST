package com.example.login_retro.Api.model;

public class Ongoing_Mission_Class {
    private String name;
    private String drone_id;
    private String weight;
    private String mission_type;
    private int id;

    public Ongoing_Mission_Class(int mission_id) {
        this.id = mission_id;
    }

    public Ongoing_Mission_Class(String name, String drone_id, String weight, String type) {
        this.name = name;
        this.drone_id = drone_id;
        this.weight = weight;
        this.mission_type = type;
    }

    public String getName() {
        return name;
    }

    public int getMission_id() {
        return id;
    }

    public void setMission_id(int mission_id) {
        this.id = mission_id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDrone_id() {
        return drone_id;
    }

    public void setDrone_id(String drone_id) {
        this.drone_id = drone_id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getType() {
        return mission_type;
    }

    public void setType(String type) {
        this.mission_type = type;
    }
}
