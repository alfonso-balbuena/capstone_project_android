package com.alfonso.capstone.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.alfonso.capstone.model.Route;
import com.alfonso.capstone.repository.IRepository;

import java.util.List;

public class MyRoutesViewModel extends ViewModel {

    private IRepository repository;
    private LiveData<List<Route>> routes;

    public MyRoutesViewModel(IRepository repository) {
        this.repository = repository;
        routes = repository.getAllRoutes();
    }

    public void addRoute(String name) {
        Route route = new Route();
        route.setName(name);
        repository.insertRoute(route);
    }

    public LiveData<List<Route>> getAllRoutes(){
        return routes;
    }
}
