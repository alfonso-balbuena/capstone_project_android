package com.alfonso.capstone.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.alfonso.capstone.model.RoutePlaceCrossRef;
import com.alfonso.capstone.model.RouteWithPlaces;

@Dao
public interface IRoutesPlacesDao {

    @Transaction
    @Query("SELECT * FROM Route WHERE routeId =:routeId")
    RouteWithPlaces getRouteWithPlaceLiveData(final long routeId);


    @Transaction
    @Query("SELECT * FROM Route WHERE routeId =:routeId")
    RouteWithPlaces getRouteWithPlace(final long routeId);

    @Insert
    void insertPlaceRoute(RoutePlaceCrossRef routePlaceCrossRef);

    @Query("SELECT * FROM RoutePlaceCrossRef WHERE placeId=:placeId AND routeId=:routeId")
    RoutePlaceCrossRef getCrossRef(String placeId,long routeId);
}
