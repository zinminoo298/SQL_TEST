package com.example.login_retro.Api.model;

public class Mission_Class {
    private String name;
    private String status;
    private String drone_id;


    public Mission_Class(String name,String status,String drone_id){

        this.name = name;
        this.status =status;
        this.drone_id = drone_id;

    }

    public String getName (){return name;}

    public void setName (String name) { this.name = name;}

    public String getStatus (){return status;}

    public void setStatus (String status) { this.status = status;}

    public String getDrone_id (){return drone_id;}

    public void setDrone_id (String drone_id) { this.drone_id = drone_id;}



}

