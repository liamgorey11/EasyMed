package com.example.easymed;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class dbPatient {
    @NonNull
    @PrimaryKey
    public String username;

    @ColumnInfo(name = "firstName")
    public String firstName;

    @ColumnInfo(name = "lastName")
    public String lastName;

    @ColumnInfo(name = "age")
    public int age;

    @ColumnInfo(name = "healthCardNum")
    public int healthCardNum;

    @ColumnInfo(name = "home address")
    public int homeAddress;

    @ColumnInfo(name = "password")
    public String password;

    public String getUsername(){
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getFirstName(){
        return firstName;
    }
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName){
        this.lastName = lastName;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public int getHealthCardNum() {
        return healthCardNum;
    }
    public void setHealthCardNum(int healthCardNum) {
        this.healthCardNum = healthCardNum;
    }
    public int getHomeAddress() {
        return homeAddress;
    }
    public void setHomeAddress(int homeAddress) {
        this.homeAddress = homeAddress;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
