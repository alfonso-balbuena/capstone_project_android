package com.alfonso.capstone.repository.imp;

import android.graphics.Bitmap;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.RoomDatabase;

import com.alfonso.capstone.database.RoutesDataBase;
import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.model.Route;
import com.alfonso.capstone.model.RoutePlaceCrossRef;
import com.alfonso.capstone.model.RouteWithPlaces;
import com.alfonso.capstone.repository.IRepository;
import com.alfonso.capstone.services.IPlaceService;
import com.google.android.libraries.places.api.model.PhotoMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;

import timber.log.Timber;

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
            Thread currentThread = new Thread(runnable);
            currentThread.start();
        }
    }

    @Override
    public void insertPlace(PlaceCapstone placeCapstone) {
        Executor executor = new ThreadTaskExecutor();
        Timber.d("Inserting place");
        executor.execute(() -> dataBase.placeDao().insertPlace(placeCapstone));
    }

    @Override
    public LiveData<List<PlaceCapstone>> getAllPlaces(LifecycleOwner owner) {
        MutableLiveData<List<PlaceCapstone>> places = new MutableLiveData<>();
        List<PlaceCapstone> data = new ArrayList<>();
        ThreadTaskExecutor executor = new ThreadTaskExecutor();
        dataBase.placeDao().getAllPlacesLiveData().observe(owner, placeCapstones -> {
            executor.execute(() -> {
                placeCapstones.forEach(place ->  {
                    place.setName(placeService.getNamePlace(place.getId()));
                    data.add(place);
                });
                Timber.d("Getting places from database...");
                places.postValue(data);
            });
        });
        return places;
    }

    @Override
    public void insertRoute(Route route) {
        Executor executor = new ThreadTaskExecutor();
        Timber.d("Inserting route " + route.getName() + " in the database");
        executor.execute(() -> dataBase.routeDao().insert(route));
    }

    @Override
    public LiveData<List<Route>> getAllRoutes() {
        Timber.d("Getting all the routes in the database");
        return dataBase.routeDao().getAllRoutesLiveData();
    }

    @Override
    public void addPlaceToRoute(long idRoute, PlaceCapstone place) {
        Executor executor = new ThreadTaskExecutor();
        RoutePlaceCrossRef routePlaceCrossRef = new RoutePlaceCrossRef(idRoute, place.getId());
        executor.execute(() -> {
            RoutePlaceCrossRef crossRef = dataBase.placesRoutesDao().getCrossRef(place.getId(),idRoute);
            if(crossRef != null)
                return;;
            PlaceCapstone placeCapstone = dataBase.placeDao().getPlaceById(place.getId());
            if (placeCapstone == null) {
                dataBase.placeDao().insertPlace(place);
            }
            Timber.d("Inserting a relationship between route " + idRoute + " and place " + place.getName());
            dataBase.placesRoutesDao().insertPlaceRoute(routePlaceCrossRef);
        });
    }

    @Override
    public LiveData<RouteWithPlaces> getRoute(long idRoute) {
        MutableLiveData<RouteWithPlaces> route = new MutableLiveData<>();
        ThreadTaskExecutor executor = new ThreadTaskExecutor();
        executor.execute(() -> {
            RouteWithPlaces routeWithPlaces = dataBase.placesRoutesDao().getRouteWithPlaceLiveData(idRoute);
            routeWithPlaces.getPlaces().forEach(pc -> {
                pc.setName(placeService.getNamePlace(pc.getId()));
            });
            Timber.d("Getting the route with all the places that has relation");
            route.postValue(routeWithPlaces);
        });
        return route;
    }

    @Override
    public LiveData<List<PlaceCapstone>> getPlaceCurrentPosition() {
        Timber.d("Getting the places of the current location...");
        return placeService.getPlacesCurrentLocation();
    }

    @Override
    public void updatePlacesCurrentPosition() {
        Timber.d("Updating the current location and the places...");
        placeService.updatePlaces();
    }

    @Override
    public LiveData<PlaceCapstone> getPlaceDetail(String id) {
        Objects.nonNull(id);
        Timber.d("Getting the detail of the place %s",id);
        return placeService.getPlaceById(id);
    }

    @Override
    public LiveData<List<Bitmap>> getPhotos(List<PhotoMetadata> metadata) {
        MutableLiveData<List<Bitmap>> photosList = new MutableLiveData<>();
        List<Bitmap> images = new ArrayList<>();
        Executor executor = new ThreadTaskExecutor();
        executor.execute(() -> {
            metadata.forEach(mData -> {
                Bitmap img = placeService.getPhoto(mData);
                if(img != null) {
                    images.add(img);
                }
            });
            Timber.d("Getting the photos of a place...");
            photosList.postValue(images);
        });
        return photosList;
    }
}
