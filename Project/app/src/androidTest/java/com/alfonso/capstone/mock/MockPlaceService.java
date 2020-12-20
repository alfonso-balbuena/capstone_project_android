package com.alfonso.capstone.mock;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.services.CallbackName;
import com.alfonso.capstone.services.IPlaceService;

import java.util.Collections;
import java.util.List;

public class MockPlaceService implements IPlaceService {
    MutableLiveData<List<PlaceCapstone>> data;

    public MockPlaceService() {
        data = new MutableLiveData<>();
    }

    @Override
    public LiveData<List<PlaceCapstone>> getPlacesCurrentLocation() {
        return data;
    }

    @Override
    public void updatePlaces() {
        PlaceCapstone placeCapstone = new PlaceCapstone();
        placeCapstone.setId("Test data");
        List<PlaceCapstone> newData = Collections.singletonList(placeCapstone);
        data.setValue(newData);
    }

    @Override
    public LiveData<PlaceCapstone> getPlaceById(String id) {
        return null;
    }

    @Override
    public void getNamePlace(String id, CallbackName callbackName) {

    }
}
