package com.alfonso.capstone.database;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.alfonso.capstone.model.Route;

import java.util.List;

@Dao
public interface IRouteDao {

    @Insert
    void insert(Route route);

    @Query("SELECT * FROM Route")
    List<Route> getAllRoutes();

    @Query("SELECT * FROM Route")
    LiveData<List<Route>> getAllRoutesLiveData();

    @Query("SELECT * FROM Route")
    Cursor getRoutes();

}
