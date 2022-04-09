package com.example.easymed;

public class Doctor extends Person{

        //Attributes
        private String workLocation;
        //Private ArrayList<Day> days; //schedule


        public Doctor(String fn, String ln, String wl )
        {
            super(fn, ln);
            this.workLocation = wl;
        }

}
