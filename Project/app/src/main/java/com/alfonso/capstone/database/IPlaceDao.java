package com.alfonso.capstone.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.alfonso.capstone.model.PlaceCapstone;

import java.util.List;

@Dao
public interface IPlaceDao {

    @Insert
    void insertPlace(PlaceCapstone placeCapstone);

    @Query("SELECT * FROM PlaceCapstone")
    List<PlaceCapstone> getAllPlaces();

    @Query("SELECT * FROM PlaceCapstone")
    LiveData<List<PlaceCapstone>> getAllPlacesLiveData();

    @Query("SELECT * FROM PlaceCapstone WHERE placeId=:id")
    LiveData<PlaceCapstone> getPlace(String id);

    @Query("SELECT * FROM PlaceCapstone WHERE placeId=:id")
    PlaceCapstone getPlaceById(String id);
}
