package com.alfonso.capstone.services.imp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.model.openTripMap.ConvertOpenTripMapToPlaceCapstone;
import com.alfonso.capstone.model.openTripMap.Feature;
import com.alfonso.capstone.model.openTripMap.FeatureRequest;
import com.alfonso.capstone.model.openTripMap.PlaceOpenTripMap;
import com.alfonso.capstone.services.IPlaceService;
import com.alfonso.capstone.services.retrofit.OpenTripMapRetrofit;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.libraries.places.api.model.PhotoMetadata;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class OpenTripMapService implements IPlaceService {

    private final Context context;
    private final MutableLiveData<List<PlaceCapstone>> places;
    private final String URI_BASE =  "https://api.opentripmap.com/0.1/";
    private final OpenTripMapRetrofit service;
    private final FusedLocationProviderClient fusedLocationProviderClient;
    private final String lang;
    private final Long radius = 150L;
    private final String API_KEY = "5ae2e3f221c38a28845f05b630483af68d31e67ea44e46210b8ccbc5";

    public OpenTripMapService(Context context){
        this.context = context;
        places = new MutableLiveData<>();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URI_BASE)
                .addConverterFactory(GsonConverterFactory.create()).build();
        service = retrofit.create(OpenTripMapRetrofit.class);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        lang = "en";
    }

    @Override
    public LiveData<List<PlaceCapstone>> getPlacesCurrentLocation() {
        Timber.d("getPlacesCurrentLocation Retrofit");
        updatePlaces();
        return places;
    }

    @Override
    public void updatePlaces() {
        if(context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                if(location != null) {
                    service.getPlaceForLocation(lang,radius,location.getLongitude(),location.getLatitude(),API_KEY).enqueue(new Callback<FeatureRequest>() {
                        @Override
                        public void onResponse(Call<FeatureRequest> call, Response<FeatureRequest> response) {
                            Timber.d(call.request().toString());
                            List<PlaceCapstone> newPlaces;
                            if(response.body() != null) {
                                newPlaces = response.body().getFeatures().stream().map(ConvertOpenTripMapToPlaceCapstone::convertToPlacesCapstone).collect(Collectors.toList());
                                Timber.d("Number of places %s", newPlaces.size());
                                places.postValue(newPlaces);
                            }
                        }
                        @Override
                        public void onFailure(Call<FeatureRequest> call, Throwable t) {
                            Timber.d("Failure %s", t.getMessage());
                            places.postValue(new ArrayList<>());
                        }
                    });

                }
            });
        }
    }

    @Override
    public LiveData<PlaceCapstone> getPlaceById(String id) {
        MutableLiveData<PlaceCapstone> place = new MutableLiveData<>();
        service.getDetail(lang,id,API_KEY).enqueue(new Callback<PlaceOpenTripMap>() {
            @Override
            public void onResponse(Call<PlaceOpenTripMap> call, Response<PlaceOpenTripMap> response) {
                    Timber.d(call.request().toString());
                   if(response.body() != null) {
                        place.postValue(ConvertOpenTripMapToPlaceCapstone.convertToPlaceCapstone(response.body()));
                   }
            }

            @Override
            public void onFailure(Call<PlaceOpenTripMap> call, Throwable t) {
                Timber.d(call.request().toString());
                Timber.d("Failure %s",t.getMessage());
            }
        });
        return place;
    }

    @Override
    public String getNamePlace(String id) {
        return null;
    }

    @Override
    public Bitmap getPhoto(PhotoMetadata metadata) {
        return null;
    }
}
