package com.alfonso.capstone.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.model.Route;
import com.alfonso.capstone.model.RoutePlaceCrossRef;

@Database(entities = {PlaceCapstone.class, Route.class, RoutePlaceCrossRef.class},version = 1,exportSchema = false)
public abstract class RoutesDataBase extends RoomDatabase {
    public abstract IPlaceDao placeDao();
    public abstract IRouteDao routeDao();
    public abstract IRoutesPlacesDao placesRoutesDao();
}
