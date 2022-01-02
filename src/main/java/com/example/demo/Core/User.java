package com.example.demo.Core;

/**
 *
 * @author Mariem
 */

public abstract class User {

    private String username, mobileNumber, email, password;
    String name;
    private boolean suspendedAccount;

    public User(String name,String username, String mobileNumber, String email, String password) {
        this.name=name;
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.password = password;
        suspendedAccount = false;
        suspendedAccount = this.getClass().getName().equals("com.example.demo.Core.Driver");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
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

    public boolean isSuspendedAccount() {
        return suspendedAccount;
    }

    public void setSuspendedAccount(boolean suspendedAccount) {
        this.suspendedAccount = suspendedAccount;
    }

}