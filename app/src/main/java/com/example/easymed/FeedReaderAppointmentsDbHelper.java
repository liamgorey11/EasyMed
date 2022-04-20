package com.example.easymed;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FeedReaderAppointmentsDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    public static final int DATABASE_VERSION = 10;
    public static final String DATABASE_NAME = "FeedReaderAppointments.db";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedReaderContract.FeedEntryAppointments.TABLE_NAME + " ( " +
                    FeedReaderContract.FeedEntryAppointments._ID + " INTEGER PRIMARY KEY," +
                    FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_USERNAME + " TEXT," +
                    FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_LOCATION + " TEXT," +
                    FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_DOCTOR + " TEXT," +
                    FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_MONTH + " TEXT," +
                    FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_DAY + " TEXT," +
                    FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_TIME + " TEXT);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedReaderContract.FeedEntryAppointments.TABLE_NAME;

    public FeedReaderAppointmentsDbHelper(Context context) {
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

    public long addValue(String username, String location, String doctor, String month, String day, String time){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_USERNAME, username);
        contentValues.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_LOCATION, location);
        contentValues.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_DOCTOR, doctor);
        contentValues.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_MONTH, month);
        contentValues.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_DAY, day);
        contentValues.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_TIME, time);
        return sqLiteDatabase.insert(FeedReaderContract.FeedEntry.TABLE_NAME, null, contentValues);
    }
    public long editValue(String username, String location, String doctor, String month, String day, String time){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_USERNAME, username);
        contentValues.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_LOCATION, location);
        contentValues.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_DOCTOR, doctor);
        contentValues.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_MONTH, month);
        contentValues.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_DAY, day);
        contentValues.put(FeedReaderContract.FeedEntryAppointments.COLUMN_NAME_TIME, time);
        return sqLiteDatabase.update(FeedReaderContract.FeedEntry.TABLE_NAME, contentValues, "username = ?", new String[]{username});
    }
    public Cursor deleteValue(String username){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + FeedReaderContract.FeedEntry.TABLE_NAME +" WHERE username = ?", new String[]{username});
        if(cursor.getCount() > 0){
            sqLiteDatabase.delete(FeedReaderContract.FeedEntry.TABLE_NAME, "username=?", new String[]{username});
        }
        return cursor;
    }
}