package com.alex.location.bean;

/**
 * User Model
 */
public class User {
    public String username;
    public String password;
    public String sensorData;
    public String email;

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String sensorData, String email) {
        this.username = username;
        this.password = password;
        this.sensorData = sensorData;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSensorData() {
        return sensorData;
    }

    public void setSensorData(String sensorData) {
        this.sensorData = sensorData;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", sensorData='" + sensorData + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
