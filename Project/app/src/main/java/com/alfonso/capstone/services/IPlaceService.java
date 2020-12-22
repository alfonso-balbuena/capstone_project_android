package com.alfonso.capstone.services;

import androidx.lifecycle.LiveData;

import com.alfonso.capstone.model.PlaceCapstone;

import java.util.List;

public interface IPlaceService {
    LiveData<List<PlaceCapstone>> getPlacesCurrentLocation();
    void updatePlaces();
    LiveData<PlaceCapstone> getPlaceById(String id);
    String getNamePlace(String id);
}
