package com.alfonso.capstone.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import androidx.fragment.app.Fragment;

import android.Manifest;

import android.content.pm.PackageManager;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfonso.capstone.R;

import com.alfonso.capstone.databinding.FragmentMapsLocationBinding;

import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.services.imp.PlaceServiceGoogle;
import com.google.android.gms.location.FusedLocationProviderClient;
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
    private final int CODE_RESULT_PERMISSION = 1;
    private FusedLocationProviderClient locationProviderClient;
    private PlaceServiceGoogle placeServiceGoogle;

    private final GoogleMap.OnInfoWindowClickListener windowClickListener = marker -> {

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
        permission();
        locationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
        placeServiceGoogle = new PlaceServiceGoogle(getContext());
        placeServiceGoogle.getPlacesCurrentLocation().observe(this, this::addMarkers);
        placeServiceGoogle.getPlaceById("ChIJ4a8nxjL50YURJ19RJ_A9T5E");
        return view;
    }

    private void addMarkers(List<PlaceCapstone> places) {
        places.forEach(placeCapstone -> {
            Log.d("DATA",placeCapstone.toString());
            map_.addMarker(new MarkerOptions().position(new LatLng(placeCapstone.getLatitude(),placeCapstone.getLongitude())).title(placeCapstone.getName()));
        });
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
            if(map_ != null) {
                map_.setMyLocationEnabled(true);
                locationProviderClient.getLastLocation().addOnSuccessListener(location -> {
                   if(location != null) {
                       map_.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),location.getLongitude()),15.0f));
                   }
                });
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODE_RESULT_PERMISSION) {
            if(permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                settingLocationEnabled();
            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setMessage(getString(R.string.permission_reason)).setTitle(getString(R.string.permission_dialog_title))
                        .setNeutralButton(R.string.ok, (dialogInterface, i) -> dialogInterface.cancel()).create();
                alertDialog.show();
            }
        }
    }
}