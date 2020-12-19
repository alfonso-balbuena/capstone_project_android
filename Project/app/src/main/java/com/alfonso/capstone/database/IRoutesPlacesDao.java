package com.alfonso.capstone.database;

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
    RouteWithPlaces getRouteWithPlace(final long routeId);

    @Insert
    void insertPlaceRoute(RoutePlaceCrossRef routePlaceCrossRef);

}
