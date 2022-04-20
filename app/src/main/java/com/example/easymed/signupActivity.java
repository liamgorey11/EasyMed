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

import java.util.ArrayList;
import java.util.List;

public class signupActivity extends AppCompatActivity {

    EditText et_username, et_password, et_firstname, et_lastname, et_age, et_healthcard, et_address;
    Button btn_back, btn_submit;

    FeedReaderDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        dbHelper = new FeedReaderDbHelper(getApplicationContext());

        et_username = findViewById(R.id.et_username);
        et_password = findViewById(R.id.et_password);
        et_firstname = findViewById(R.id.et_firstname);
        et_lastname = findViewById(R.id.et_lastname);
        et_age = findViewById(R.id.et_age);
        et_healthcard = findViewById(R.id.et_healthcard);
        et_address = findViewById(R.id.et_address);
        btn_back = findViewById(R.id.btn_back);
        btn_submit = findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  //TODO: CHECK USER'S INFORMATION IS GOOD
                  Runnable objRunnable = new Runnable() {
                      @Override
                      public void run() {
                          SQLiteDatabase db = dbHelper.getWritableDatabase();

                          //Do a db query across all users to retrieve all usernames
                          Cursor cursor = db.query(
                                  FeedReaderContract.FeedEntry.TABLE_NAME,   // The table to query
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

                          int flag = 0; //flag to check if a username already exists
                          for (int i = 0; i < usernames.size(); i++) {
                              Log.d("usernames: ", usernames.get(i).toString());
                              if (usernames.get(i).toString().equals(et_username.getText().toString())) { //if we search the db and find a user who has the same username, increment flag
                                  flag++;
                              }
                          }
                          if(flag != 0){ //flag will be 0 if the username is unique
                              signupActivity.this.runOnUiThread(new Runnable() {
                                  public void run() {
                                      Toast.makeText(signupActivity.this, "Username already in use", Toast.LENGTH_SHORT).show();
                                  }
                              });
                          }
                          else if(!temp.isEmpty()){
                              signupActivity.this.runOnUiThread(new Runnable() {
                                  public void run() {
                                      Toast.makeText(signupActivity.this, loginCriteria(et_username.getText().toString(), et_password.getText().toString()), Toast.LENGTH_SHORT).show();
                                  }
                              });
                          }
                          else if(et_username.getText().toString().isEmpty() || et_password.getText().toString().isEmpty() || et_firstname.getText().toString().isEmpty() || et_lastname.getText().toString().isEmpty() || et_age.getText().toString().isEmpty() || et_healthcard.getText().toString().isEmpty() || et_address.getText().toString().isEmpty()){
                              signupActivity.this.runOnUiThread(new Runnable() {
                                  public void run() {
                                      Toast.makeText(signupActivity.this, "Please fill all slots", Toast.LENGTH_SHORT).show();
                                  }
                              });
                          }
                          else{
                              dbHelper.addValue(et_username.getText().toString(), et_password.getText().toString(), et_firstname.getText().toString(), et_lastname.getText().toString(), et_age.getText().toString(), et_healthcard.getText().toString(), et_address.getText().toString());

                              Intent intent= new Intent(signupActivity.this, loginActivity.class);
                              intent.putExtra("username", et_username.getText().toString());
                              startActivity(intent);
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

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(signupActivity.this, loginActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onDestroy() { //getWritableDatabase() and getReadableDatabase() are expensive to call when the db is closed, so try to leave it open as long as possible
        dbHelper.close();
        super.onDestroy();
    }

    public void clearTable(SQLiteDatabase db){
        db.execSQL("DELETE FROM "+FeedReaderContract.FeedEntry.TABLE_NAME);
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