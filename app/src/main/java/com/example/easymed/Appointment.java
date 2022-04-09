package com.example.easymed;

import android.text.format.Time;

import java.util.Date;

public class Appointment {

    //Attributes
    private Patient patient;
    private Doctor doctor;
    private String location; //location of appointment
    private Date date; //date of appointment
    private Time time; //time of appointment

    public Appointment(Patient p, Doctor doc, String l, Date d, Time t )
    {
        this.patient = p;
        this.doctor = doc;
        this.location = l;
        this.date = d;
        this.time = t;
    }
}
