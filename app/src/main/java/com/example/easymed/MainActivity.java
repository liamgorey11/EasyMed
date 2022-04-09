package com.example.easymed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/*Home menu (landing page/window)
Should handle appointment creation logic
 */
public class MainActivity extends AppCompatActivity {

    Button create_btn, logout_btn, delete_btn, edit_btn, personal_info_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        create_btn = findViewById(R.id.create_app_btn);
        delete_btn = findViewById(R.id.delete_app_btn);
        edit_btn = findViewById(R.id.edit_app_btn);
        logout_btn = findViewById(R.id.logout_btn);
        personal_info_btn = findViewById(R.id.personal_info_btn);

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent_add = new Intent(MainActivity.this, createAppointment.class);
                startActivity(intent_add);
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent_add = new Intent(MainActivity.this, delete_appointment.class);
                startActivity(intent_add);
            }
        });

        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent_add = new Intent(MainActivity.this, edit_appointment.class);
                startActivity(intent_add);
            }
        });

        personal_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent_add = new Intent(MainActivity.this, personal_info.class);
                startActivity(intent_add);
            }
        });

        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish(); //exits app
            }
        });


    }
}