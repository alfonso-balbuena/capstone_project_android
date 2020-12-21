package com.alfonso.capstone.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfonso.capstone.CapstoneApplication;
import com.alfonso.capstone.R;

import com.alfonso.capstone.activities.DetailPlace;
import com.alfonso.capstone.databinding.FragmentMapsLocationBinding;

import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.services.imp.PlaceServiceGoogle;
import com.alfonso.capstone.viewmodel.DetailPlaceViewModel;
import com.alfonso.capstone.viewmodel.MapsLocationViewModel;
import com.alfonso.capstone.viewmodel.factory.ViewModelRepositoryFactory;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapsLocationFragment extends Fragment {

    private GoogleMap map_;
    private FragmentMapsLocationBinding binding;
    private MapsLocationViewModel viewModel;
    private final int CODE_RESULT_PERMISSION = 1;
    private FusedLocationProviderClient locationProviderClient;
    private LocationRequest locationRequest;
    private final long UPDATE_INTERVAL = 120000;
    private LocationCallback locationCallback;
    private boolean requestingLocationUpdates;

    private final GoogleMap.OnInfoWindowClickListener windowClickListener = marker -> {
        Intent intent = new Intent(requireActivity(), DetailPlace.class);
        intent.putExtra(DetailPlace.PLACE_ID, marker.getTag().toString());
        startActivity(intent);
    };

    private final OnMapReadyCallback callback = new OnMapReadyCallback() {
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map_ = googleMap;
            map_.setOnInfoWindowClickListener(windowClickListener);
            map_.setMinZoomPreference(15.0f);
            map_.setMaxZoomPreference(18.0f);
            settingLocationEnabled();
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentMapsLocationBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity(), new ViewModelRepositoryFactory<MapsLocationViewModel>(((CapstoneApplication) requireActivity().getApplication()).repository)).get(MapsLocationViewModel.class);
        permission();
        initLocationRequest();
        viewModel.getPlacesCurrentPosition().observe(this, this::addMarkers);
        return view;
    }

    private void addMarkers(List<PlaceCapstone> places) {
        places.forEach(placeCapstone -> {
            map_.addMarker(new MarkerOptions().position(new LatLng(placeCapstone.getLatitude(), placeCapstone.getLongitude())).title(placeCapstone.getName()).snippet(getString(R.string.click_info))).setTag(placeCapstone.getId());
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!requestingLocationUpdates) {
            initUpdatesCurrentLocation();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        locationProviderClient.removeLocationUpdates(locationCallback);
        requestingLocationUpdates = false;
    }

    private void initUpdatesCurrentLocation() {
        if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult == null)
                        return;
                    map_.clear();
                    viewModel.updatePlacesForPosition();
                    map_.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(locationResult.getLastLocation().getLatitude(), locationResult.getLastLocation().getLongitude()), 18.0f));
                }
            };
            requestingLocationUpdates = true;
            locationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        }
    }

    private void initLocationRequest() {
        locationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(UPDATE_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    private void permission() {
        if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            settingLocationEnabled();
        } else {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, CODE_RESULT_PERMISSION);
        }
    }

    public void settingLocationEnabled() {
        if (getContext().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (map_ != null) {
                map_.setMyLocationEnabled(true);
                locationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                    if (location != null) {
                        map_.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 18.0f));
                    }
                });
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODE_RESULT_PERMISSION) {
            if (permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                settingLocationEnabled();
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setMessage(getString(R.string.permission_reason)).setTitle(getString(R.string.permission_dialog_title))
                        .setNeutralButton(R.string.ok, (dialogInterface, i) -> dialogInterface.cancel()).create();
                alertDialog.show();
            }
        }
    }
}