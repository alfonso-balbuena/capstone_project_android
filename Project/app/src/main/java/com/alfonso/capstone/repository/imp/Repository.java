package com.alfonso.capstone.repository.imp;

import android.content.Context;
import android.graphics.Bitmap;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alfonso.capstone.database.RoutesDataBase;
import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.model.Route;
import com.alfonso.capstone.model.RoutePlaceCrossRef;
import com.alfonso.capstone.model.RouteWithPlaces;
import com.alfonso.capstone.repository.IRepository;
import com.alfonso.capstone.services.IPlaceService;
import com.google.android.libraries.places.api.model.PhotoMetadata;

import java.security.InvalidParameterException;
import java.util.ArrayList;
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
        private Thread currentThread;

        @Override
        public void execute(Runnable runnable) {
            currentThread = new Thread(runnable);
            currentThread.start();
        }

        public void sleep() {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void insertPlace(PlaceCapstone placeCapstone) {
        Executor executor = new ThreadTaskExecutor();
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
                places.postValue(data);
            });
        });
        return places;
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
        RoutePlaceCrossRef routePlaceCrossRef = new RoutePlaceCrossRef(idRoute, place.getId());
        executor.execute(() -> {
            RoutePlaceCrossRef crossRef = dataBase.placesRoutesDao().getCrossRef(place.getId(),idRoute);
            if(crossRef != null)
                return;;
            PlaceCapstone placeCapstone = dataBase.placeDao().getPlaceById(place.getId());
            if (placeCapstone == null) {
                dataBase.placeDao().insertPlace(place);
            }
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
            route.postValue(routeWithPlaces);
        });
        return route;
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
            photosList.postValue(images);
        });
        return photosList;
    }
}
