package com.alfonso.capstone.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.alfonso.capstone.CapstoneApplication;
import com.alfonso.capstone.R;
import com.alfonso.capstone.databinding.FragmentDetailPlaceBinding;
import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.model.Route;
import com.alfonso.capstone.viewmodel.DetailPlaceViewModel;
import com.alfonso.capstone.viewmodel.factory.ViewModelRepositoryFactory;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import timber.log.Timber;

public class DetailPlaceFragment extends Fragment implements OnMapReadyCallback, AdapterView.OnItemSelectedListener {

    private FragmentDetailPlaceBinding binding;
    private DetailPlaceViewModel viewModel;
    private GoogleMap map_;
    private PlaceCapstone placeDetail;
    private long idRoute = -1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDetailPlaceBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity(),new ViewModelRepositoryFactory<DetailPlaceViewModel>(((CapstoneApplication)requireActivity().getApplication()).repository)).get(DetailPlaceViewModel.class);
        init();

        binding.btnAddToRoute.setOnClickListener(view -> {
            if(placeDetail != null && idRoute != -1) {
                viewModel.addPlaceToRoute(idRoute,placeDetail);
                androidx.appcompat.app.AlertDialog alertDialog = new AlertDialog.Builder(getContext()).setMessage(getString(R.string.add_place)).setTitle(getString(R.string.success))
                        .setNeutralButton(R.string.ok, (dialogInterface, i) -> dialogInterface.cancel()).create();
                alertDialog.show();
            }
        });
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_detail);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    private void init() {
        viewModel.getRoutes().observe(this,routes -> {
            ArrayAdapter<Route> adapter = new ArrayAdapter<>(getContext(),R.layout.spinner_item,routes);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            binding.spinnerRoutes.setOnItemSelectedListener(this);
            binding.spinnerRoutes.setAdapter(adapter);
        });

        viewModel.getIdPlace().observe(this,idPlace -> {
            viewModel.getPlace(idPlace).observe(this, placeCapstone -> {
                binding.detailName.setText(placeCapstone.getName());
                binding.phoneDetail.setText(placeCapstone.getPhone());
                binding.ratingPlaceDetail.setRating(placeCapstone.getRating().floatValue());
                binding.addresDetail.setText(placeCapstone.getAddress());
                if(placeCapstone.getImage() != null && !placeCapstone.getImage().isEmpty()) {
                    Timber.d("Getting the image " + placeCapstone.getImage());
                    Picasso.get().setIndicatorsEnabled(true);
                    Picasso.get().setLoggingEnabled(true);
                    Picasso.get().load(placeCapstone.getImage()).into(binding.photoPlace);
                }

                placeDetail = placeCapstone;

                if(map_ != null) {
                    addPlace();
                }
            });
        });
    }

    private void addPlace() {
        if(placeDetail != null) {
            map_.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(placeDetail.getLatitude(),placeDetail.getLongitude()),15.0f));
            map_.addMarker(new MarkerOptions().position(new LatLng(placeDetail.getLatitude(),placeDetail.getLongitude())).title(placeDetail.getName()));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map_ = googleMap;
        map_.setMinZoomPreference(15.0f);
        map_.setMaxZoomPreference(18.0f);
        addPlace();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Route r = (Route) adapterView.getItemAtPosition(i);
        idRoute = r.getIdRoute();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}