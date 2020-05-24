package com.example.docviewer;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class member {
    private String email;
    private String username;
    private String password;
    private HashMap<Integer, Boolean> map;

    member(String email,String username,String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
