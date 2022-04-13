package com.example.easymed;
import androidx.room.Database;
import androidx.room.RoomDatabase;


@Database(entities = {dbPatient.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract dbPatientDao patientDao();

}
