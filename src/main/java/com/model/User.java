package com.model;


import com.input.create.UserFormCreate;
import java.io.Serializable;


public class User implements Serializable {

    private long id;
    private String userName;
    private String email;
    private String password;
    private Double balance;

    public User(long id, String userName, String email){
        this.id = id;
        this.userName = userName;
        this.email = email;
    }

	public User() {
	}

	public User(UserFormCreate userFormCreate){
    	this.userName = userFormCreate.getUserName();
    	this.email = userFormCreate.getEmail();
    	this.password = userFormCreate.getPassword();
	}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
