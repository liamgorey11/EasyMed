package com.example.easymed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/*Home menu (landing page/window)
Should handle appointment creation logic
 */
public class MainActivity extends AppCompatActivity {

    Button create_btn, logout_btn, personal_info_btn,view_btn;

    private AppDatabase db;
    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        try{
            Bundle extras = getIntent().getExtras();
            if(!(extras == null)){
                username = extras.getString("username");
            }
        }
        catch(Exception e){
            Log.d("Error: ", "Error getting extras");
        }

        create_btn = findViewById(R.id.create_app_btn);
        logout_btn = findViewById(R.id.logout_btn);
        personal_info_btn = findViewById(R.id.personal_info_btn);
        view_btn = findViewById(R.id.view_app_btn);

        create_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent_add = new Intent(MainActivity.this, createAppointmentActivity.class);
                intent_add.putExtra("username", username);
                startActivity(intent_add);
            }
        });

        personal_info_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent_add = new Intent(MainActivity.this, personal_infoActivity.class);
                intent_add.putExtra("username", username);
                startActivity(intent_add);
            }
        });
        view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_view = new Intent(MainActivity.this, ViewAppointment.class);
                startActivity(intent_view);
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