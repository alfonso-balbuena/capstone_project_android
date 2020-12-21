package com.alfonso.capstone.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.model.Route;
import com.alfonso.capstone.repository.IRepository;

import java.util.List;

public class DetailPlaceViewModel extends ViewModel {

    private final MutableLiveData<String> idPlace;
    private final IRepository repository;

    public DetailPlaceViewModel(IRepository repository) {
        idPlace = new MutableLiveData<>();
        this.repository = repository;
    }

    public LiveData<String> getIdPlace() {
        return idPlace;
    }

    public LiveData<PlaceCapstone> getPlace(String id) {
        return repository.getPlaceDetail(id);
    }

    public LiveData<List<Route>> getRoutes() {
        return repository.getAllRoutes();
    }

    public void addPlaceToRoute(long idRoute,PlaceCapstone place) {
        repository.addPlaceToRoute(idRoute,place);
    }

    public void setIdPlace(String idPlace) {
        this.idPlace.setValue(idPlace);
    }
}
