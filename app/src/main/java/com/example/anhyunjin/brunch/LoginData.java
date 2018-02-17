package com.example.anhyunjin.brunch;

public class LoginData {
    private String email;
    private String ps;

    public LoginData(){ }

    public LoginData(String email, String ps) {
        this.email = email;
        this.ps = ps;
    }
    public String getEmail(){return this.email;}
    public String getPs(){return this.ps;}
}
