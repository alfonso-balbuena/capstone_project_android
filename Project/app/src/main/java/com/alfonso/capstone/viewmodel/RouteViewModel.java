package com.alfonso.capstone.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alfonso.capstone.model.RouteWithPlaces;
import com.alfonso.capstone.repository.IRepository;

public class RouteViewModel extends ViewModel {

    private final IRepository repository;
    private MutableLiveData<Long> idRoute;

    public RouteViewModel(IRepository repository) {
        this.repository = repository;
        idRoute = new MutableLiveData<Long>();
    }

    public LiveData<Long> getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(long idRoute) {
        this.idRoute.setValue(idRoute);
    }

    public LiveData<RouteWithPlaces> getPlacesForId() {
        return repository.getRoute(idRoute.getValue());
    }
}
