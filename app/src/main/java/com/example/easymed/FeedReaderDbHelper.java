package com.example.easymed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedReaderDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 5;
    public static final String DATABASE_NAME = "FeedReader.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntry.TABLE_NAME + "(" +
                    FeedReaderContract.FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_USERNAME + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_FIRSTNAME + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_LASTNAME + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_AGE + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_HEALTHCARD + " TEXT," +
                    FeedReaderContract.FeedEntry.COLUMN_NAME_ADDRESS + " TEXT);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntry.TABLE_NAME;

    public FeedReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long addValue(String username, String password, String fname, String lname, String age, String healthcard, String address){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_USERNAME, username);
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD, password);
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_FIRSTNAME, fname);
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LASTNAME, lname);
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_AGE, age);
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_HEALTHCARD, healthcard);
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ADDRESS, address);
        return sqLiteDatabase.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, contentValues);
    }
    public long editValue(String username, String password, String fname, String lname, String age, String healthcard, String address){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_PASSWORD, password);
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_FIRSTNAME, fname);
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_LASTNAME, lname);
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_AGE, age);
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_HEALTHCARD, healthcard);
        contentValues.put(FeedReaderContract.FeedEntry.COLUMN_NAME_ADDRESS, address);
        return sqLiteDatabase.update(FeedReaderContract.FeedEntry.TABLE_NAME, contentValues, "username = ?", new String[]{username});
    }
}

