package com.example.easymed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ViewAppointment extends AppCompatActivity {
    Button back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);

        //Goes back to main activity
        back = findViewById(R.id.back_btn_view);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(ViewAppointment.this,MainActivity.class);
                startActivity(intent_back);
            }
        });

        //PULL FROM DATABASE AND ADD CURRENT APPS TO CALENDAR
    }
}