package com.example.easymed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class createAppointmentActivity extends AppCompatActivity {

    EditText et_location;
    EditText et_doctor;
    EditText et_date;
    EditText et_time;
    Button submit_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        et_location = findViewById(R.id.editText_location);
        et_doctor = findViewById(R.id.editText_doctor);
        et_date= findViewById(R.id.editText_date);
        et_time= findViewById(R.id.editText_time);

        submit_btn = findViewById(R.id.submit_btn);

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
        });

    }
}