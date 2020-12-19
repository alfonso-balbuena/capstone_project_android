package com.alfonso.capstone.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alfonso.capstone.model.PlaceCapstone;

import java.util.List;

@Dao
public interface IPlaceDao {

    @Insert
    void insertPlate(PlaceCapstone placeCapstone);

    @Query("SELECT * FROM PlaceCapstone")
    List<PlaceCapstone> getAllPlaces();

    @Query("SELECT * FROM PlaceCapstone WHERE placeId=:id")
    PlaceCapstone getPlace(String id);
}
