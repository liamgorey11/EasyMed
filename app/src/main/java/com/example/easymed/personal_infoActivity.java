package com.example.easymed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class personal_infoActivity extends AppCompatActivity {

    EditText et_username, et_password, et_firstname, et_lastname, et_age, et_healthcard, et_address;
    Button btn_back2, btn_submit2;

    FeedReaderDbHelper dbHelper;

    String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);

        dbHelper = new FeedReaderDbHelper(getApplicationContext());

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_firstname = findViewById(R.id.et_firstname);
        et_lastname = findViewById(R.id.et_lastname);
        et_age = findViewById(R.id.et_age);
        et_healthcard = findViewById(R.id.et_healthcard);
        et_address = findViewById(R.id.et_address);
        btn_back2 = findViewById(R.id.btn_back2);
        btn_submit2 = findViewById(R.id.btn_submit2);

        try{
            Bundle extras = getIntent().getExtras();
            if(!(extras == null)){
                username = extras.getString("username");

                Runnable objRunnable = new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase db = dbHelper.getReadableDatabase();

                        // Filter results WHERE "title" = 'My Title'
                        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_USERNAME + " = ?";
                        String[] selectionArgs = {username};

                        Cursor cursor = db.query(
                                "entry",   // The table to query
                                null,             // The array of columns to return (pass null to get all)
                                selection,              // The columns for the WHERE clause
                                selectionArgs,          // The values for the WHERE clause
                                null,                   // don't group the rows
                                null,                   // don't filter by row groups
                                null               // The sort order
                        );

                        while (cursor.moveToNext()) {
                            String name = cursor.getString(1);
                            String password = cursor.getString(2);
                            String firstname = cursor.getString(3);
                            String lastname = cursor.getString(4);
                            String age = cursor.getString(5);
                            String healthcard = cursor.getString(6);
                            String address = cursor.getString(7);
                            et_username.setText(name);
                            et_password.setText(password);
                            et_firstname.setText(firstname);
                            et_lastname.setText(lastname);
                            et_age.setText(age);
                            et_healthcard.setText(healthcard);
                            et_address.setText(address);
                            Log.i("set starting values: ", "start values set");
                        }
                        cursor.close();
                    }
                };

                Thread objBgThread = new Thread(objRunnable);
                objBgThread.start();
            }
        }
        catch(Exception e){
            Log.i("Error: ", "Error getting extras");
        }

        btn_submit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: CHECK USER'S INFORMATION IS GOOD
                Runnable objRunnable = new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase db = dbHelper.getReadableDatabase();


                        Cursor cursor = db.query(
                                "entry",   // The table to query
                                null,             // The array of columns to return (pass null to get all)
                                null,              // The columns for the WHERE clause
                                null,          // The values for the WHERE clause
                                null,                   // don't group the rows
                                null,                   // don't filter by row groups
                                null               // The sort order
                        );


                        List usernames = new ArrayList<>();
                        String temp = loginCriteria(et_username.getText().toString(), et_password.getText().toString());
                        while (cursor.moveToNext()) {
                            String name = cursor.getString(1);
                            usernames.add(name);
                        }
                        cursor.close();

                        int flag = 0; //flag to check if the user changed their username, if the username is already in use
                        for (int i = 0; i < usernames.size(); i++) {
                            Log.d("usernames: ", usernames.get(i).toString());
                            if (usernames.get(i).toString().equals(et_username.getText().toString()) && !usernames.get(i).toString().equals(username)) { //if we search the db and find a user who has the same username, increment flag
                                flag++;
                            }
                        }
                        if(flag != 0){ //flag will be 0 if the username is unique
                            personal_infoActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(personal_infoActivity.this, "Username already in use", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else if(!temp.isEmpty()){ //check for more specific criteria like length of password/username
                            personal_infoActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(personal_infoActivity.this, loginCriteria(et_username.getText().toString(), et_password.getText().toString()), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        else if(et_username.getText().toString().isEmpty() || et_password.getText().toString().isEmpty() || et_firstname.getText().toString().isEmpty() || et_lastname.getText().toString().isEmpty() || et_age.getText().toString().isEmpty() || et_healthcard.getText().toString().isEmpty() || et_address.getText().toString().isEmpty()){
                            personal_infoActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(personal_infoActivity.this, "Please fill all slots", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        else{
                            // Filter results WHERE "title" = 'My Title'
                            String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_USERNAME + " = ?";
                            String[] selectionArgs = new String[]{username};

                            ContentValues values = new ContentValues();
                            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_USERNAME, et_username.getText().toString());
                            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD, et_password.getText().toString());
                            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_FIRSTNAME, et_firstname.getText().toString());
                            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LASTNAME, et_lastname.getText().toString());
                            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_AGE, et_age.getText().toString());
                            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_HEALTHCARD, et_healthcard.getText().toString());
                            values.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ADDRESS, et_address.getText().toString());



                            int count = db.update(
                                    FeedReaderContract.FeedEntry.TABLE_NAME,
                                    values,
                                    selection,
                                    selectionArgs
                            );
                            username = et_username.getText().toString(); //We don't know if the user typed in a new username but we update the global variable in case. If the username was not changed then this line does nothing.
                            personal_infoActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(personal_infoActivity.this, "success: "+count+" rows changed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                        cursor = db.query(
                                "entry",   // The table to query
                                null,             // The array of columns to return (pass null to get all)
                                null,              // The columns for the WHERE clause
                                null,          // The values for the WHERE clause
                                null,                   // don't group the rows
                                null,                   // don't filter by row groups
                                null               // The sort order
                        );



                        while (cursor.moveToNext()) {
                            List profile = new ArrayList<>();
                            String user = cursor.getString(1);
                            String pass = cursor.getString(2);
                            String first = cursor.getString(3);
                            String last = cursor.getString(4);
                            profile.add(user);
                            profile.add(pass);
                            profile.add(first);
                            profile.add(last);
                            //Log.d("Usernames: ", String.valueOf(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_USERNAME)));
                            //test.add(name);
                            Log.d("profile: ", profile.toString());
                        }
                        cursor.close();

                        //deleteRow(new String[]{String.valueOf(et_username.getText().toString())}, db);
                        //clearTable(db);
                        //dropTable(db);
                    }
                };
                Thread objBgThread = new Thread(objRunnable);
                objBgThread.start();
            }
        });

        btn_back2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(personal_infoActivity.this, MainActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);

            }
        });
    }
    public String loginCriteria(String username, String password){ //We're enforcing some basic criteria for viable username and password entries when users create new accounts
        int num_digits = 0;
        for(int i = 0; i<password.length(); i++){
            if((int)password.charAt(i) >= 48 && (int)password.charAt(i) <= 57){
                num_digits++;
            }
        }
        if(username.length() < 3){
            return "Username must be at least 3 characters";
        }
        else if(password.length() < 3){
            return "Password must be at least 3 characters";
        }
        else if(num_digits == 0){
            return "Password must contain at least 1 number";
        }
        else{
            return "";
        }
    }
}