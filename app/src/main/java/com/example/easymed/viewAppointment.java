package com.example.easymed;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class viewAppointment extends AppCompatActivity {
    Button back, vButton;
    databaseHelper dbase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);

        vButton = findViewById(R.id.view_button);
        dbase = new databaseHelper(this);

        viewApptData();
        //Goes back to main activity
        back = findViewById(R.id.back_btn_view);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(viewAppointment.this,MainActivity.class);
                startActivity(intent_back);
            }
        });


    }
    //PULL FROM DATABASE AND Give option to ADD CURRENT APPt TO CALENDAR
    public void viewApptData(){
        vButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor apptData = dbase.getDataAppt();
                if(apptData.getCount() == 0){
                    Toast.makeText(viewAppointment.this,"NO DATA",Toast.LENGTH_LONG).show();
                    showData("Your Appointments","No valid Appts for your username");
                    return;
                }else{
                    StringBuffer buff = new StringBuffer();
                    while(apptData.moveToNext()){//shows the string at index of table
                        buff.append("id: "+apptData.getString(0)+"\n");
                        buff.append("Doctor: "+apptData.getString(1)+"\n");
                        buff.append("Your Name:"+apptData.getString(2)+"\n");
                        buff.append("Location: "+apptData.getString(3)+"\n");
                        buff.append("Day: "+apptData.getString(4)+"\n");
                        buff.append("Time : "+apptData.getString(5)+"\n\n");
                    }
                    showData("Your Appointments", buff.toString());

                }
            }
        });

    }
    public void showData(String title,String data){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);//could write (YOUR APPTS)
        builder.setMessage(data);
        builder.show();
    }

}