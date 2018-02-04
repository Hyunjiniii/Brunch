package com.example.anhyunjin.brunch;

public class LoginData {
    private String username;
    private String email;
    private String ps;

    public LoginData(){ }

    public LoginData(String username, String email, String ps) {
        this.username = username;
        this.email = email;
        this.ps = ps;
    }
    public String getUsername(){return this.username;}
    public String getEmail(){return this.email;}
    public String getPs(){return this.ps;}
}
