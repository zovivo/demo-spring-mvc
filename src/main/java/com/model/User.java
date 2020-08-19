package com.model;


import com.input.create.UserFormCreate;
import com.input.update.UserFormUpdate;

import java.io.Serializable;


public class User implements Serializable {

    private long id;
    private String userName;
    private String name;
    private String avatar;
    private String email;
    private String password;
    private Double balance = 0.0;

    public User(long id, String userName, String email){
        this.id = id;
        this.userName = userName;
        this.email = email;
    }

	public User() {
	}

	public User(UserFormCreate userFormCreate){
    	this.userName = userFormCreate.getUserName();
    	this.name = userFormCreate.getName();
    	this.email = userFormCreate.getEmail();
    	this.password = userFormCreate.getPassword();
	}

    public User(UserFormUpdate userFormUpdate){
        this.id = userFormUpdate.getId();
        this.name = userFormUpdate.getName();
        this.password = userFormUpdate.getPassword();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
