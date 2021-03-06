package com.example.easymed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class editAppointmentActivity extends AppCompatActivity {

    String username;
    String location;
    String doctor;
    String date;
    String time;
    Button submit_btn, back_btn;

    FeedReaderLocationDbHelper dbLocationsHelper;
    FeedReaderAppointmentsDbHelper dbAppointmentsHelper;
    FeedReaderDoctorDbHelper dbDoctorsHelper;
    Spinner spinner_location, spinner_month, spinner_day, spinner_time, spinner_doctor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_appointment);

        try{
            Bundle extras = getIntent().getExtras();
            if(!(extras == null)){
                username = extras.getString("username");
                location = extras.getString("location");
                doctor = extras.getString("doctor");
                date = extras.getString("date");
                time = extras.getString("time");
            }
        }
        catch(Exception e){
            Log.d("Error: ", "Error getting extras");
        }

        spinner_location = findViewById(R.id.spinner_location);
        spinner_month = findViewById(R.id.spinner_month);
        spinner_day = findViewById(R.id.spinner_day);
        spinner_time = findViewById(R.id.spinner_time);
        spinner_doctor = findViewById(R.id.spinner_doctor);
        submit_btn = findViewById(R.id.submit_btn);
        back_btn = findViewById(R.id.back_btn);
        dbLocationsHelper = new FeedReaderLocationDbHelper(getApplicationContext());
        SQLiteDatabase dbLocations = dbLocationsHelper.getReadableDatabase();
        dbAppointmentsHelper = new FeedReaderAppointmentsDbHelper(getApplicationContext());
        SQLiteDatabase dbAppointments = dbAppointmentsHelper.getWritableDatabase();
        dbDoctorsHelper = new FeedReaderDoctorDbHelper(editAppointmentActivity.this.getApplicationContext());
        SQLiteDatabase dbDoctors = dbDoctorsHelper.getWritableDatabase();

        Runnable objRunnable = new Runnable() {
            @Override
            public void run() {



                ContentValues values = new ContentValues();
                values.put(FeedReaderContract.FeedEntryDoctors.COLUMN_NAME_DOCTORNAME, "Dr. Jones");
                dbDoctors.insert(FeedReaderContract.FeedEntryDoctors.TABLE_NAME, null, values);
                values.put(FeedReaderContract.FeedEntryDoctors.COLUMN_NAME_DOCTORNAME, "Dr. Stephens");
                dbDoctors.insert(FeedReaderContract.FeedEntryDoctors.TABLE_NAME, null, values);
                values.put(FeedReaderContract.FeedEntryDoctors.COLUMN_NAME_DOCTORNAME, "Dr. Smith");
                dbDoctors.insert(FeedReaderContract.FeedEntryDoctors.TABLE_NAME, null, values);

                values = new ContentValues();
                values.put(FeedReaderContract.FeedEntryLocations.COLUMN_NAME_LOCATIONNAME, "Windsor");
                dbLocations.insert(FeedReaderContract.FeedEntryLocations.TABLE_NAME, null, values);
                values.put(FeedReaderContract.FeedEntryLocations.COLUMN_NAME_LOCATIONNAME, "Toronto");
                dbLocations.insert(FeedReaderContract.FeedEntryLocations.TABLE_NAME, null, values);
                values.put(FeedReaderContract.FeedEntryLocations.COLUMN_NAME_LOCATIONNAME, "Ottawa");
                dbLocations.insert(FeedReaderContract.FeedEntryLocations.TABLE_NAME, null, values);
                values.put(FeedReaderContract.FeedEntryLocations.COLUMN_NAME_LOCATIONNAME, "Detroit");
                dbLocations.insert(FeedReaderContract.FeedEntryLocations.TABLE_NAME, null, values);
                values.put(FeedReaderContract.FeedEntryLocations.COLUMN_NAME_LOCATIONNAME, "Tecumseh");
                dbLocations.insert(FeedReaderContract.FeedEntryLocations.TABLE_NAME, null, values);



                Cursor cursor = dbLocations.query(
                        "locations",   // The table to query
                        null,             // The array of columns to return (pass null to get all)
                        null,              // The columns for the WHERE clause
                        null,          // The values for the WHERE clause
                        null,                   // don't group the rows
                        null,                   // don't filter by row groups
                        null               // The sort order
                );
                String[] columns = new String[]{FeedReaderContract.FeedEntryLocations.COLUMN_NAME_LOCATIONNAME};
                int[] to = new int[]{android.R.id.text1};
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, cursor, columns, to, 0);
                spinner_location.setAdapter(adapter);

                cursor = dbDoctors.query(
                        "doctors",   // The table to query
                        null,             // The array of columns to return (pass null to get all)
                        null,              // The columns for the WHERE clause
                        null,          // The values for the WHERE clause
                        null,                   // don't group the rows
                        null,                   // don't filter by row groups
                        null               // The sort order
                );
                columns = new String[]{FeedReaderContract.FeedEntryDoctors.COLUMN_NAME_DOCTORNAME};
                adapter = new SimpleCursorAdapter(editAppointmentActivity.this.getApplicationContext(), android.R.layout.simple_list_item_1, cursor, columns, to, 0);
                spinner_doctor.setAdapter(adapter);
                //cursor.close();
            }
        };
        Thread objBgThread = new Thread(objRunnable);
        objBgThread.start();

        try {
            objBgThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.timeslots_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_time.setAdapter(adapter);

        ArrayAdapter<CharSequence> month_adapter = ArrayAdapter.createFromResource(this, R.array.month_array, android.R.layout.simple_spinner_item);
        month_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_month.setAdapter(month_adapter);

        ArrayAdapter<CharSequence> day_adapter = ArrayAdapter.createFromResource(this, R.array.day_array, android.R.layout.simple_spinner_item);
        day_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(day_adapter);


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable objRunnable = new Runnable() {
                    @Override
                    public void run() {

                        TextView txt = (TextView) spinner_location.getSelectedView(); //cast spinner selected view to textview
                        String loc = txt.getText().toString(); //extract content of textview
                        txt = (TextView) spinner_doctor.getSelectedView();
                        String doc = txt.getText().toString();
                        String month = String.valueOf(spinner_month.getSelectedItem());
                        String day = String.valueOf(spinner_day.getSelectedItem());
                        String time = String.valueOf(spinner_time.getSelectedItem());

                        if(loc.isEmpty() || doc.isEmpty() || month.isEmpty() || day.isEmpty() || time.isEmpty()){ //check for more specific criteria like length of password/username
                            editAppointmentActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(editAppointmentActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else{
                            // Filter results WHERE "title" = 'My Title'
                            String selection = FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_USERNAME + " = ?";
                            String[] selectionArgs = new String[]{username};

                            ContentValues values = new ContentValues();
                            values.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_USERNAME, username);
                            values.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_LOCATION, loc);
                            values.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_DOCTOR, doc);
                            values.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_MONTH, month);
                            values.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_DAY, day);
                            values.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_TIME, time);

                            int count = dbAppointments.update(
                                    FeedReaderContract.FeedEntryAppointments.TABLE_NAME,
                                    values,
                                    selection,
                                    selectionArgs
                            );
                            editAppointmentActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(editAppointmentActivity.this, "success: "+count+" rows changed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        //deleteRow(new String[]{String.valueOf(et_username.getText().toString())}, db);
                        //clearTable(db);
                        //dropTable(db);
                    }
                };
                Thread objBgThread = new Thread(objRunnable);
                objBgThread.start();
                try {
                    objBgThread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), editAppointmentActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);
            }
        });
    }
}