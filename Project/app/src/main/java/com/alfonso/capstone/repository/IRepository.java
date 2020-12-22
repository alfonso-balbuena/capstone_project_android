package com.alfonso.capstone.repository;

import android.graphics.Bitmap;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.model.Route;
import com.alfonso.capstone.model.RouteWithPlaces;
import com.google.android.libraries.places.api.model.PhotoMetadata;

import java.security.InvalidParameterException;
import java.util.List;

public interface IRepository {
    void insertPlace(PlaceCapstone placeCapstone);
    LiveData<List<PlaceCapstone>> getAllPlaces(LifecycleOwner owner);
    void insertRoute(Route route);
    LiveData<List<Route>> getAllRoutes();
    void addPlaceToRoute(long idRoute, PlaceCapstone place);
    LiveData<RouteWithPlaces> getRoute(long idRoute);
    LiveData<List<PlaceCapstone>> getPlaceCurrentPosition();
    void updatePlacesCurrentPosition();
    LiveData<PlaceCapstone> getPlaceDetail(String id);
    LiveData<List<Bitmap>> getPhotos(List<PhotoMetadata> metadata);
}
