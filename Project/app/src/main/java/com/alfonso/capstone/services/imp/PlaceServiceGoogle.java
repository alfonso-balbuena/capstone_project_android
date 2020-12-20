package com.alfonso.capstone.services.imp;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.alfonso.capstone.R;
import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.services.IPlaceService;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest;
import com.google.android.libraries.places.api.net.FindCurrentPlaceResponse;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PlaceServiceGoogle implements IPlaceService {

    private final Context context;
    private final MutableLiveData<List<PlaceCapstone>> places;
    private final PlacesClient placesClient;
    private final List<Place.Type> typePlacesFilter;
    private final List<Place.Field> typePlacesDetail;
    private final List<Place.Field> placeFields;

    public PlaceServiceGoogle(Context context) {
        this.context = context;
        this.places = new MutableLiveData<>();
        Places.initialize(context,context.getString(R.string.google_maps_key));
        placesClient = Places.createClient(context);
        typePlacesFilter = Arrays.asList(Place.Type.AIRPORT,Place.Type.AIRPORT,Place.Type.AQUARIUM,Place.Type.ART_GALLERY,Place.Type.CHURCH,Place.Type.HINDU_TEMPLE,Place.Type.ZOO,Place.Type.TOURIST_ATTRACTION,Place.Type.STADIUM,Place.Type.MUSEUM,Place.Type.MOVIE_THEATER);
        placeFields = Arrays.asList(Place.Field.NAME, Place.Field.ID, Place.Field.LAT_LNG, Place.Field.TYPES);
        typePlacesDetail = Arrays.asList(Place.Field.ID,Place.Field.NAME,Place.Field.PHOTO_METADATAS,Place.Field.ADDRESS,Place.Field.RATING);
    }

    @Override
    public LiveData<List<PlaceCapstone>> getPlacesCurrentLocation() {
        updatePlaces();
        return places;
    }

    @Override
    public void updatePlaces() {
        FindCurrentPlaceRequest request = FindCurrentPlaceRequest.newInstance(placeFields);
        if(context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Task<FindCurrentPlaceResponse> placeResponseTask = placesClient.findCurrentPlace(request);
            placeResponseTask.addOnCompleteListener(task -> {
                if(task.isSuccessful()) {
                    FindCurrentPlaceResponse response = task.getResult();
                    List<PlaceCapstone> p = response.getPlaceLikelihoods().stream()
                            .filter(placeLikelihood -> typePlacesFilter.stream().anyMatch(type -> Objects.requireNonNull(placeLikelihood.getPlace().getTypes()).contains(type)))
                            .map(placeLikelihood -> new PlaceCapstone(placeLikelihood.getPlace().getId(),placeLikelihood.getPlace().getName(),placeLikelihood.getPlace().getLatLng().latitude,placeLikelihood.getPlace().getLatLng().longitude))
                            .collect(Collectors.toList());
                    places.setValue(p);
                }
            });
        }
    }

    @Override
    public LiveData<PlaceCapstone> getPlaceById(String id) {
        MutableLiveData<PlaceCapstone> place = new MutableLiveData<>();
        FetchPlaceRequest request = FetchPlaceRequest.newInstance(id,typePlacesDetail);

        placesClient.fetchPlace(request).addOnSuccessListener(fetchPlaceResponse -> {
           Log.d("SERVICE",fetchPlaceResponse.getPlace().toString());
        });

        return place;
    }
}