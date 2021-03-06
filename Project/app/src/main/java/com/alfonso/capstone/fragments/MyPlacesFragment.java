package com.alfonso.capstone.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfonso.capstone.CapstoneApplication;
import com.alfonso.capstone.activities.DetailPlace;
import com.alfonso.capstone.adapter.GenericAdapter;
import com.alfonso.capstone.databinding.FragmentMyPlacesBinding;
import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.viewmodel.MyPlacesViewModel;
import com.alfonso.capstone.viewmodel.SharePlaceViewModel;
import com.alfonso.capstone.viewmodel.factory.ViewModelRepositoryFactory;


public class MyPlacesFragment extends Fragment {

    private FragmentMyPlacesBinding binding;
    private MyPlacesViewModel viewModel;
    private GenericAdapter<PlaceCapstone> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMyPlacesBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity(),new ViewModelRepositoryFactory<MyPlacesViewModel>(((CapstoneApplication)requireActivity().getApplication()).repository)).get(MyPlacesViewModel.class);
        initRV();
        return binding.getRoot();
    }

    private void initRV() {
        binding.rvMyPlaces.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvMyPlaces.setLayoutManager(layoutManager);
        adapter = new GenericAdapter<>(model -> {
            SharePlaceViewModel sharePlaceViewModel = new ViewModelProvider(requireActivity()).get(SharePlaceViewModel.class);
            sharePlaceViewModel.setPlaceId(model.getId());
        });
        viewModel.getPlaces(this).observe(this,placeCapstones -> {
            adapter.setList(placeCapstones);
        });
        binding.rvMyPlaces.setAdapter(adapter);
    }
}