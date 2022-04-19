package com.example.easymed;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.HandlerCompat;
import androidx.room.Room;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.BaseColumns;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class loginActivity extends AppCompatActivity {

    Button login_btn, signup_btn;
    EditText et_username;
    EditText et_password;
    FeedReaderDbHelper dbHelper;


    Handler objHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        dbHelper = new FeedReaderDbHelper(getApplicationContext());


        login_btn = findViewById(R.id.login_btn);
        signup_btn = findViewById(R.id.signup_btn);
        et_username = findViewById(R.id.editText_username);
        et_password = findViewById(R.id.editText_password);

        try{
            String temp = "";
            Bundle extras = getIntent().getExtras();
            if(!(extras == null)){
                temp = extras.getString("username");
            }
            et_username.setText(temp);
        }
        catch(Exception e){
            Log.d("Error: ", "Error getting extras");
        }

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable objRunnable = new Runnable() {
                    @Override
                    public void run() {
                        SQLiteDatabase db = dbHelper.getReadableDatabase();

                        // Define a projection that specifies which columns from the database
                        // you will actually use after this query.
                        String[] projection = {
                                BaseColumns._ID,
                                FeedReaderContract.FeedEntry.COLUMN_NAME_USERNAME,
                                FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD
                        };

                        // Filter results WHERE "title" = 'My Title'
                        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_USERNAME + " = ?";
                        String[] selectionArgs = {"username", "password"};

                        // How you want the results sorted in the resulting Cursor
                        String sortOrder = FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD + " DESC";

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
                        List passwords = new ArrayList<>();
                        while (cursor.moveToNext()) {
                            long itemId = cursor.getLong(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_USERNAME));
                            String name = cursor.getString(1);
                            String pass = cursor.getString(2);
                            //Log.d("Cursor: ", String.valueOf(cursor.getColumnIndexOrThrow(FeedReaderContract.FeedEntry.COLUMN_NAME_TITLE)));
                            usernames.add(name);
                            passwords.add(pass);
                        }
                        cursor.close();

                        int i;
                        for(i = 0; i<usernames.size(); i++){
                            if(usernames.get(i).toString().equals(et_username.getText().toString()) && passwords.get(i).toString().equals(et_password.getText().toString())){
                                //TODO: query the db to load in the user's information

                                Intent intent_add = new Intent(loginActivity.this, MainActivity.class);
                                intent_add.putExtra("username", et_username.getText().toString());
                                startActivity(intent_add);
                                break; //breaking here will stop the toast from showing in the new activity when a user enters good credentials
                            }
                        }
                        if(i == usernames.size()){
                            loginActivity.this.runOnUiThread(new Runnable() {
                                public void run() {
                                    Toast.makeText(loginActivity.this, "invalid username or password", Toast.LENGTH_SHORT).show();
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
            }
        });

        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(loginActivity.this, signupActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() { //getWritableDatabase() and getReadableDatabase() are expensive to call when the db is closed, so try to leave it open as long as possible
        dbHelper.close();
        super.onDestroy();
    }

    public int deleteRow(String [] Selection, SQLiteDatabase db){
        // Define 'where' part of query.
        String selection = FeedReaderContract.FeedEntry.COLUMN_NAME_USERNAME + " LIKE ?";
        // Specify arguments in placeholder order.
        String[] selectionArgs = Selection;
        // Issue SQL statement.
        int deletedRows = db.delete(FeedReaderContract.FeedEntry.TABLE_NAME, selection, selectionArgs);
        Log.d("deleted: ", String.valueOf(deletedRows));
        return deletedRows;
    }

    public void clearTable(SQLiteDatabase db){

        //String table = FeedReaderContract.FeedEntry.TABLE_NAME;
        //String whereClause = BaseColumns._ID+" = ?";
        //String[] whereArgs = new String[] { BaseColumns._ID };
       // db.rawQuery("DELETE FROM entry", null);
        db.execSQL("DELETE FROM "+FeedReaderContract.FeedEntry.TABLE_NAME);
    }
    public void dropTable(SQLiteDatabase db){

        //String table = FeedReaderContract.FeedEntry.TABLE_NAME;
        //String whereClause = BaseColumns._ID+" = ?";
        //String[] whereArgs = new String[] { BaseColumns._ID };
        // db.rawQuery("DELETE FROM entry", null);
        db.execSQL("DROP TABLE "+FeedReaderContract.FeedEntry.TABLE_NAME);
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

       /*
       login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable objRunnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (db.patientDao().userExists(et_username.getText().toString(), et_password.getText().toString())) {
                                loginActivity.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(loginActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                loginActivity.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(loginActivity.this, "invalid username or password", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (Exception e) {
                            Toast.makeText(loginActivity.this, "database error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                        objHandler.sendEmptyMessage(0);
                    }
                };

                Thread objBgThread = new Thread(objRunnable);
                objBgThread.start();


                //currently no check, just moves to mainactivity
                //Intent intent_add = new Intent(loginActivity.this, MainActivity.class);
                //startActivity(intent_add);

            }
        });


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Runnable objRunnable = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (db.patientDao().usernameExists(et_username.getText().toString())) {
                                loginActivity.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(loginActivity.this, "username already in use", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            } else {
                                loginActivity.this.runOnUiThread(new Runnable() {
                                    public void run() {
                                        Toast.makeText(loginActivity.this, "account created", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        } catch (Exception e) {
                            Toast.makeText(loginActivity.this, "database error" + e.toString(), Toast.LENGTH_SHORT).show();
                        }

                        objHandler.sendEmptyMessage(0);
                    }

                    //Intent intent_add = new Intent(loginActivity.this, MainActivity.class);
                    //startActivity(intent_add);
                };
                Thread objBgThread = new Thread(objRunnable);
                objBgThread.start();
            }
        });
        */