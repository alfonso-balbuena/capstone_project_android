package com.alfonso.capstone.services;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;

import com.alfonso.capstone.model.PlaceCapstone;
import com.google.android.libraries.places.api.model.PhotoMetadata;

import java.util.List;

public interface IPlaceService {
    LiveData<List<PlaceCapstone>> getPlacesCurrentLocation();
    void updatePlaces();
    LiveData<PlaceCapstone> getPlaceById(String id);
    String getNamePlace(String id);
    Bitmap getPhoto(PhotoMetadata metadata);
}
