package com.alfonso.capstone.database;

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

}
