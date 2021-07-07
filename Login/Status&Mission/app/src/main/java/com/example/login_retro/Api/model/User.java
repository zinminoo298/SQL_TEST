package com.example.login_retro.Api.model;

public class User {
    private int id;
    private String email;
    private String token;


    public void setId(int id){this.id = id;}

    public int getId (){return id;}

    public void setExp(int id){this.id = id;}

    public String getEmail (){return email;}

    public void setEmail (String email) { this.email = email;}

    public String getToken() { return token;}

    public void setToken (String token) {this.token = token;}


}
