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

    //getter

    public Patient getPatient()
    {
        return this.patient;
    }

    public Doctor getDoctor()
    {
        return this.doctor;
    }

    public String getLocation()
    {
        return this.location;
    }

    public Date getDate()
    {
        return this.date;
    }

    public Time getTime()
    {
        return this.time;
    }

    //setter

    public void setPatient(Patient p)
    {
        this.patient = p;
    }


    public void setDoctor(Doctor d)
    {
        this.doctor = d;
    }

    public void setLocation(String l)
    {
        this.location = l;
    }

    public void setDate(Date d)
    {
        this.date = d;
    }
    public void setTime(Time t)
    {
        this.time = t;
    }


}
