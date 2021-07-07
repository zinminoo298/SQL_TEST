package com.example.login_retro.Api.model;

public class Ongoing_Mission_Class {
    private String mission_name;
    private String item_weight;
    private String drone_id;
    private String missin_type;


    public Ongoing_Mission_Class(String mission_name, String item_weight, String drone_id, String missin_type) {
        this.mission_name = mission_name;
        this.item_weight = item_weight;
        this.drone_id = drone_id;
        this.missin_type = missin_type;
    }

    public String getMission_name() {
        return mission_name;
    }

    public void setMission_name(String mission_name) {
        this.mission_name = mission_name;
    }

    public String getItem_weight() {
        return item_weight;
    }

    public void setItem_weight(String item_weight) {
        this.item_weight = item_weight;
    }

    public String getDrone_id() {
        return drone_id;
    }

    public void setDrone_id(String drone_id) {
        this.drone_id = drone_id;
    }

    public String getMissin_type() {
        return missin_type;
    }

    public void setMissin_type(String missin_type) {
        this.missin_type = missin_type;
    }
}
