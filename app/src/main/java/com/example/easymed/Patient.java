package com.example.easymed;

public class Patient extends Person{
    //Attributes
    private int age; //change to D.O.B if we have time
    private int healthCardNumber;
    private String homeAddress; //might change to different data type later(or change to Postal code)

    public Patient(String fn, String ln,int age, int hc_number, String h_address )
    {
        super(fn, ln);
        this.age = age;
        this.healthCardNumber = hc_number;
        this.homeAddress = h_address;
    }
}
