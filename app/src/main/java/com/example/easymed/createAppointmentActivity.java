package com.example.easymed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class createAppointmentActivity extends AppCompatActivity {

    EditText et_location;
    EditText et_doctor;
    EditText et_date;
    EditText et_time;
    EditText et_patient;
    Button submit_btn;

    databaseHelper dbase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        dbase = new databaseHelper(this);

        et_location = findViewById(R.id.editText_location);
        et_doctor = findViewById(R.id.editText_doctor);
        et_date= findViewById(R.id.editText_date);
        et_time= findViewById(R.id.editText_time);
        et_patient= findViewById(R.id.editText_patient);

        submit_btn = findViewById(R.id.submit_btn);
        addData();

        /*
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String l = et_location.getText().toString();
                String doc = et_doctor.getText().toString();
                String d = et_date.getText().toString();
                String t = et_time.getText().toString();

                Intent intent_add = new Intent(createAppointmentActivity.this, MainActivity.class);
                intent_add.putExtra("location", "" + l);
                intent_add.putExtra("doctor", "" + doc);
                intent_add.putExtra("date", "" + d);
                intent_add.putExtra("time", "" + t);
                startActivity(intent_add);
            }
        });*/

    }
    public void addData(){
        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean isInserted = dbase.insertDataAppt(et_doctor.getText().toString(),et_patient.getText().toString(),et_location.getText().toString(),et_date.getText().toString(),et_time.getText().toString());
                if(isInserted = true){
                    Toast.makeText(createAppointmentActivity.this,"APPT SUCCESSFULLY CREATED",Toast.LENGTH_LONG).show();
                    //make intent to switch to different activity's when they make apt
                }else{
                    Toast.makeText(createAppointmentActivity.this,"ERROR MAKING APPT PLEASE TRY AGAIN",Toast.LENGTH_LONG).show();
                }
            }
        });
    }



}