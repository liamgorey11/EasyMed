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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class createAppointmentActivity extends AppCompatActivity {

    Button submit_btn, back_btn;


    Spinner spinner_location, spinner_doctor, spinner_month, spinner_day, spinner_time;
    FeedReaderLocationDbHelper dbHelper;
    FeedReaderAppointmentsDbHelper dbAppointmentsHelper;
    FeedReaderDoctorDbHelper dbDoctorsHelper;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        try{
            Bundle extras = getIntent().getExtras();
            if(!(extras == null)){
                username = extras.getString("username");
            }
        }
        catch(Exception e){
            Log.d("Error: ", "Error getting extras");
        }

        spinner_location = findViewById(R.id.spinner_location);
        spinner_doctor = findViewById(R.id.spinner_doctor);
        spinner_month = findViewById(R.id.spinner_month);
        spinner_day = findViewById(R.id.spinner_day);
        spinner_time = findViewById(R.id.spinner_time);
        submit_btn = findViewById(R.id.submit_btn);
        back_btn = findViewById(R.id.back_btn);
        dbHelper = new FeedReaderLocationDbHelper(createAppointmentActivity.this.getApplicationContext());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        dbAppointmentsHelper = new FeedReaderAppointmentsDbHelper(createAppointmentActivity.this.getApplicationContext());
        SQLiteDatabase dbAppointments = dbAppointmentsHelper.getWritableDatabase();
        dbDoctorsHelper = new FeedReaderDoctorDbHelper(createAppointmentActivity.this.getApplicationContext());
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
                db.insert(FeedReaderContract.FeedEntryLocations.TABLE_NAME, null, values);
                values.put(FeedReaderContract.FeedEntryLocations.COLUMN_NAME_LOCATIONNAME, "Toronto");
                db.insert(FeedReaderContract.FeedEntryLocations.TABLE_NAME, null, values);
                values.put(FeedReaderContract.FeedEntryLocations.COLUMN_NAME_LOCATIONNAME, "Ottawa");
                db.insert(FeedReaderContract.FeedEntryLocations.TABLE_NAME, null, values);
                values.put(FeedReaderContract.FeedEntryLocations.COLUMN_NAME_LOCATIONNAME, "Detroit");
                db.insert(FeedReaderContract.FeedEntryLocations.TABLE_NAME, null, values);
                values.put(FeedReaderContract.FeedEntryLocations.COLUMN_NAME_LOCATIONNAME, "Tecumseh");
                db.insert(FeedReaderContract.FeedEntryLocations.TABLE_NAME, null, values);






                Cursor cursor = db.query(
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
                SimpleCursorAdapter adapter = new SimpleCursorAdapter(createAppointmentActivity.this.getApplicationContext(), android.R.layout.simple_list_item_1, cursor, columns, to, 0);
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
                adapter = new SimpleCursorAdapter(createAppointmentActivity.this.getApplicationContext(), android.R.layout.simple_list_item_1, cursor, columns, to, 0);
                spinner_doctor.setAdapter(adapter);
            }
        };
        Thread objBgThread = new Thread(objRunnable);
        objBgThread.start();


        /* The other adapters are created using hardcoded string arrays in strings.xml, and are used for universal data that doesn't change much*/
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> time_adapter = ArrayAdapter.createFromResource(this, R.array.timeslots_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        time_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_time.setAdapter(time_adapter);

        ArrayAdapter<CharSequence> month_adapter = ArrayAdapter.createFromResource(this, R.array.month_array, android.R.layout.simple_spinner_item);
        month_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_month.setAdapter(month_adapter);

        ArrayAdapter<CharSequence> day_adapter = ArrayAdapter.createFromResource(this, R.array.day_array, android.R.layout.simple_spinner_item);
        day_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_day.setAdapter(day_adapter);


        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                TextView temp = (TextView) spinner_location.getSelectedView(); //cast spinner selected view to textview
                String loc = temp.getText().toString(); //extract content of textview
                temp = (TextView) spinner_doctor.getSelectedView();
                String doc = temp.getText().toString();
                String month = String.valueOf(spinner_month.getSelectedItem());
                String day = String.valueOf(spinner_day.getSelectedItem());
                String time = String.valueOf(spinner_time.getSelectedItem());


                //Insert appointment data to db
                ContentValues values = new ContentValues();
                values.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_USERNAME, username);
                values.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_LOCATION, loc);
                values.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_DOCTOR, doc);
                values.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_MONTH, month);
                values.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_DAY, day);
                values.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_TIME, time);
                long a = dbAppointments.insert(FeedReaderContract.FeedEntryAppointments.TABLE_NAME, null, values);



                //go back to main activity
                Intent intent_add = new Intent(createAppointmentActivity.this, MainActivity.class);
                intent_add.putExtra("username", username);
                startActivity(intent_add);
            }
        });
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //dropTable(dbAppointments);
                Intent intent_add = new Intent(createAppointmentActivity.this, MainActivity.class);
                intent_add.putExtra("username", username);
                startActivity(intent_add);
            }
        });
    }
    public void dropTable(SQLiteDatabase db){

        //String table = FeedReaderContract.FeedEntry.TABLE_NAME;
        //String whereClause = BaseColumns._ID+" = ?";
        //String[] whereArgs = new String[] { BaseColumns._ID };
        // db.rawQuery("DELETE FROM entry", null);
        db.execSQL("DROP TABLE "+FeedReaderContract.FeedEntryAppointments.TABLE_NAME);
    }
}