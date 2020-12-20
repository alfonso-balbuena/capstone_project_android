package com.alfonso.capstone.repository.imp;

import androidx.lifecycle.LiveData;

import com.alfonso.capstone.database.RoutesDataBase;
import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.model.Route;
import com.alfonso.capstone.model.RoutePlaceCrossRef;
import com.alfonso.capstone.model.RouteWithPlaces;
import com.alfonso.capstone.repository.IRepository;
import com.alfonso.capstone.services.IPlaceService;

import java.security.InvalidParameterException;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

public class Repository implements IRepository {

    private final RoutesDataBase dataBase;
    private final IPlaceService placeService;

    public Repository(RoutesDataBase dataBase, IPlaceService placeService) {
        this.dataBase = dataBase;
        this.placeService = placeService;
    }

    public static class ThreadTaskExecutor implements Executor {
        @Override
        public void execute(Runnable runnable) {
            new Thread(runnable).start();
        }
    }

    @Override
    public void insertPlace(PlaceCapstone placeCapstone) {
        Executor executor = new ThreadTaskExecutor();
        executor.execute(() -> dataBase.placeDao().insertPlace(placeCapstone));
    }

    @Override
    public LiveData<List<PlaceCapstone>> getAllPlaces() {
        return dataBase.placeDao().getAllPlacesLiveData();
    }

    @Override
    public void insertRoute(Route route) {
        Executor executor = new ThreadTaskExecutor();
        executor.execute(() -> dataBase.routeDao().insert(route));
    }

    @Override
    public LiveData<List<Route>> getAllRoutes() {
        return dataBase.routeDao().getAllRoutesLiveData();
    }

    @Override
    public void addPlaceToRoute(long idRoute, PlaceCapstone place) {
        Executor executor = new ThreadTaskExecutor();
        RoutePlaceCrossRef routePlaceCrossRef = new RoutePlaceCrossRef(idRoute,place.getId());
        executor.execute(() -> {
            PlaceCapstone placeCapstone = dataBase.placeDao().getPlaceById(place.getId());
            if(placeCapstone == null) {
                dataBase.placeDao().insertPlace(place);
            }
            dataBase.placesRoutesDao().insertPlaceRoute(routePlaceCrossRef);
        });
    }

    @Override
    public LiveData<RouteWithPlaces> getRoute(long idRoute) {
        return dataBase.placesRoutesDao().getRouteWithPlaceLiveData(idRoute);
    }

    @Override
    public LiveData<List<PlaceCapstone>> getPlaceCurrentPosition() {
        return placeService.getPlacesCurrentLocation();
    }

    @Override
    public void updatePlacesCurrentPosition() {
        placeService.updatePlaces();
    }

    @Override
    public LiveData<PlaceCapstone> getPlaceDetail(String id) {
        Objects.nonNull(id);
        return placeService.getPlaceById(id);
    }
}