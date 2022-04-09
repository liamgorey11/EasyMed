package com.example.easymed;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface dbPatientDao {
    //sample QUERYS
    @Query("SELECT * FROM dbPatient")
    List <dbPatient> getAll();

    /*
    donut need
    @Query("SELECT * FROM dbPatient WHERE username IN (:username)")
    List<dbPatient> loadAllByUsername(String[] username);
    */

    @Query("SELECT * FROM dbPatient WHERE firstName LIKE :first AND "+"lastName LIKE :last LIMIT 1")
    dbPatient findByName(String first, String last);

    @Insert
    void insertAll(dbPatient... users);

    @Delete
    void delete(dbPatient user);

    @Update
    int update(dbPatient user);
}
