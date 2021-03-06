package com.example.easymed;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewAppointment extends AppCompatActivity {

    RecyclerView recyclerView;
    Button back;

    RecyclerAdapter adapter;
    String username;
    FeedReaderAppointmentsDbHelper dbAppointmentsHelper;


    ArrayList<String> al_location;
    ArrayList<String> al_doctor;
    ArrayList<String> al_month;
    ArrayList<String> al_day;
    ArrayList<String> al_time;

    Runnable objRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);
        dbAppointmentsHelper = new FeedReaderAppointmentsDbHelper(ViewAppointment.this.getApplicationContext());
        SQLiteDatabase db = dbAppointmentsHelper.getReadableDatabase();
        al_location = new ArrayList<>();
        al_doctor = new ArrayList<>();
        al_month = new ArrayList<>();
        al_day = new ArrayList<>();
        al_time = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView);
        back = findViewById(R.id.back_btn_view);

        try{
            Bundle extras = getIntent().getExtras();
            if(!(extras == null)){
                username = extras.getString("username");
            }
        }
        catch(Exception e){
            Log.d("Error: ", "Error getting extras");
        }

        //PULL FROM DATABASE AND ADD CURRENT APPS TO CALENDAR
        objRunnable = new Runnable() {
            @Override
            public void run() {


                /*
                ContentValues values = new ContentValues();
                values.put(FeedReaderContract.FeedEntryLocations.COLUMN_NAME_LOCATIONNAME, "Windsor");
                db.insert(FeedReaderContract.FeedEntryLocations.TABLE_NAME, null, values);
                values.put(FeedReaderContract.FeedEntryLocations.COLUMN_NAME_LOCATIONNAME, "Toronto");
                db.insert(FeedReaderContract.FeedEntryLocations.TABLE_NAME, null, values);
                values.put(FeedReaderContract.FeedEntryLocations.COLUMN_NAME_LOCATIONNAME, "Ottawa");
                db.insert(FeedReaderContract.FeedEntryLocations.TABLE_NAME, null, values);
                values.put(FeedReaderContract.FeedEntryLocations.COLUMN_NAME_LOCATIONNAME, "Detroit");
                db.insert(FeedReaderContract.FeedEntryLocations.TABLE_NAME, null, values);
                values.put(FeedReaderContract.FeedEntryLocations.COLUMN_NAME_LOCATIONNAME, "Tecumseh");
                db.insert(FeedReaderContract.FeedEntryLocations.TABLE_NAME, null, values);
                */

                String selection = FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_USERNAME + " = ?";
                String[] selectionArgs = {username};


                Cursor cursor = db.query(
                        "appointments",   // The table to query
                        null,             // The array of columns to return (pass null to get all)
                        selection,              // The columns for the WHERE clause
                        selectionArgs,          // The values for the WHERE clause
                        null,                   // don't group the rows
                        null,                   // don't filter by row groups
                        null               // The sort order
                );
                while(cursor.moveToNext()){
                    String location = cursor.getString(2);
                    String doctor = cursor.getString(3);
                    String month = cursor.getString(4);
                    String day = cursor.getString(5);
                    String time = cursor.getString(6);
                    al_location.add(location);
                    al_doctor.add(doctor);
                    al_month.add(month);
                    al_day.add(day);
                    al_time.add(time);
                }
                cursor.close();
            }
        };
        Thread objBgThread = new Thread(objRunnable);
        objBgThread.start();

        //Set adapter
        adapter = new RecyclerAdapter(ViewAppointment.this, username, al_location, al_doctor, al_month, al_day, al_time);
        recyclerView.setLayoutManager(new LinearLayoutManager(ViewAppointment.this));
        recyclerView.setAdapter(adapter);

        //Goes back to main activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent_back = new Intent(ViewAppointment.this,MainActivity.class);
                intent_back.putExtra("username", username);
                startActivity(intent_back);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            al_location = new ArrayList<>();
            al_doctor = new ArrayList<>();
            al_month = new ArrayList<>();
            al_day = new ArrayList<>();
            al_time = new ArrayList<>();
            Thread objBgThread = new Thread(objRunnable);
            objBgThread.start();
            adapter = new RecyclerAdapter(ViewAppointment.this, username, al_location, al_doctor, al_month, al_day, al_time);
            recyclerView.setLayoutManager(new LinearLayoutManager(ViewAppointment.this));
            recyclerView.setAdapter(adapter);
            // Log.i("asd", arrayList_items.toString());
            adapter.notifyDataSetChanged(); //notify data set has changed on resume so that if we use another activity to
            // add some items, the changes will be reflected immediately

        } catch (Exception e) {
            Log.i("failed", "notify failed");
        }
    }
}