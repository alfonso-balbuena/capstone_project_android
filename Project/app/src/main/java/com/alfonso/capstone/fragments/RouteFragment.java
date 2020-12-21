package com.alfonso.capstone.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfonso.capstone.CapstoneApplication;
import com.alfonso.capstone.R;
import com.alfonso.capstone.activities.DetailPlace;
import com.alfonso.capstone.adapter.GenericAdapter;
import com.alfonso.capstone.databinding.FragmentRouteBinding;
import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.model.Route;
import com.alfonso.capstone.viewmodel.RouteViewModel;
import com.alfonso.capstone.viewmodel.factory.RouteViewModelFactory;

public class RouteFragment extends Fragment {

    private FragmentRouteBinding binding;
    private GenericAdapter<PlaceCapstone> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRouteBinding.inflate(inflater, container, false);
        RouteViewModel viewModel = new ViewModelProvider(requireActivity(),new RouteViewModelFactory(((CapstoneApplication)requireActivity().getApplication()).repository)).get(RouteViewModel.class);
        viewModel.getIdRoute().observe(this,aLong -> {
            viewModel.getPlacesForId().observe(this, routeWithPlaces -> {
                binding.rvRoutePlaces.setHasFixedSize(true);
                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                binding.rvRoutePlaces.setLayoutManager(layoutManager);
                adapter = new GenericAdapter<>(model -> {
                    Intent intent = new Intent(requireActivity(), DetailPlace.class);
                    intent.putExtra(DetailPlace.PLACE_ID,model.getId());
                    startActivity(intent);
                });
                adapter.setList(routeWithPlaces.getPlaces());
                binding.rvRoutePlaces.setAdapter(adapter);
            });
        });
        return binding.getRoot();
    }
}