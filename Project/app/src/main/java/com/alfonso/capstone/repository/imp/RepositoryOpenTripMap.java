package com.alfonso.capstone.repository.imp;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alfonso.capstone.database.RoutesDataBase;
import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.model.RouteWithPlaces;
import com.alfonso.capstone.services.IPlaceService;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;
/**
 * For this repository, the name in the entity PlaceCapstone should be save in the database
 *
 * */
public class RepositoryOpenTripMap extends Repository {

    public RepositoryOpenTripMap(RoutesDataBase dataBase, IPlaceService placeService) {
        super(dataBase, placeService);
    }

    @Override
    public LiveData<List<PlaceCapstone>> getAllPlaces(LifecycleOwner owner) {
        return dataBase.placeDao().getAllPlacesLiveData();
    }


    @Override
    public LiveData<RouteWithPlaces> getRoute(long idRoute) {
        MutableLiveData<RouteWithPlaces> route = new MutableLiveData<>();
        ThreadTaskExecutor executor = new ThreadTaskExecutor();
        executor.execute(() -> {
            Timber.d("Getting the route with all the places that has relation");
            RouteWithPlaces routeWithPlaces = dataBase.placesRoutesDao().getRouteWithPlaceLiveData(idRoute);
            route.postValue(routeWithPlaces);
        });
        return route;
    }
}
