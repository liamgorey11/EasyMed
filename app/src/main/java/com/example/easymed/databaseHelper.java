package com.example.easymed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class databaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "database.db";
    public static final String TABLE_NAME1 = "dbPatients";
    public static final String TABLE_NAME2 = "dbAppt";
    public static final String TABLE_NAME3 = "dbDoctors";

    public static final String COL_1 = "username";
    public static final String COL_2= "firstName";
    public static final String COL_3 = "lastName";
    public static final String COL_4 = "age";
    public static final String COL_5 = "healthCardNu";
    public static final String COL_6 = "homeAddress";
    public static final String COL_7 = "password";
    
    public static final String APPCOL_1 = "id";
    public static final String APPCOL_2= "doctor";
    public static final String APPCOL_3 = "patient";
    public static final String APPCOL_4 = "location";
    public static final String APPCOL_5 = "day";
    public static final String APPCOL_6 = "time";

    public static final String DOCCOL_1 = "id";
    public static final String DOCCOL_2= "firstname";
    public static final String DOCCOL_3 = "lastname";
    public static final String DOCCOL_4 = "office_location";
    public static final String DOCCOL_5 = "doctor_schedule";

    public databaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("Create table " + TABLE_NAME1 + "(USERNAME TEXT PRIMARY KEY, FIRSTNAME TEXT, LASTNAME TEXT, AGE INTEGER, HEALTHCARDNUM TEXT, HOMEADDRESS TEXT, PASSWORD TEXT)");
        db.execSQL("Create table " + TABLE_NAME2 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, DOCTOR TEXT, PATIENT TEXT, LOCATION TEXT, DAY TEXT, TIME TEXT)");
        db.execSQL("Create table " + TABLE_NAME3 + "(ID INTEGER PRIMARY KEY AUTOINCREMENT, FIRSTNAME TEXT, LASTNAME TEXT, OFFICE_LOCATION TEXT, DOCTOR_SCHEDULE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME1);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(db);
    }

    //method to insert data for APPTS
    public boolean insertDataAppt(String doctor, String patient, String location, String day, String time){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValue = new ContentValues();
        contentValue.put(APPCOL_2,doctor);
        contentValue.put(APPCOL_3,patient);
        contentValue.put(APPCOL_4,location);
        contentValue.put(APPCOL_5,day);
        contentValue.put(APPCOL_6,time);
        long check = db.insert(TABLE_NAME2,null,contentValue);
        if(check == -1){
            return false;
        }else{
            return true;
        }

    }

    //gets all data from APPT table Created in createAppoinmentActivity.java
    public Cursor getDataAppt(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor apptData = db.rawQuery("SELECT * FROM " +TABLE_NAME2,null);
        return apptData;
    }
}
