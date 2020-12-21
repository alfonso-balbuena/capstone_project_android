package com.alfonso.capstone.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.repository.IRepository;

import java.util.List;

public class MyPlacesViewModel extends ViewModel {

    private IRepository repository;

    public MyPlacesViewModel(IRepository repository) {
        this.repository = repository;
    }

    public LiveData<List<PlaceCapstone>> getPlaces(LifecycleOwner owner) {
        return repository.getAllPlaces(owner);
    }

}
