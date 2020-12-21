package com.alfonso.capstone.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.repository.IRepository;

import java.util.List;

public class MapsLocationViewModel extends ViewModel {

    private IRepository repository;

    public MapsLocationViewModel(IRepository repository){
        this.repository = repository;

    }

    public LiveData<List<PlaceCapstone>> getPlacesCurrentPosition(){
        return this.repository.getPlaceCurrentPosition();
    }

    public void updatePlacesForPosition() {
        this.repository.updatePlacesCurrentPosition();
    }
}
