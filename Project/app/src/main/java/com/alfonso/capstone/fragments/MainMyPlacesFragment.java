package com.alfonso.capstone.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfonso.capstone.CapstoneApplication;
import com.alfonso.capstone.R;
import com.alfonso.capstone.activities.DetailPlace;
import com.alfonso.capstone.viewmodel.DetailPlaceViewModel;
import com.alfonso.capstone.viewmodel.SharePlaceViewModel;
import com.alfonso.capstone.viewmodel.factory.ViewModelRepositoryFactory;

public class MainMyPlacesFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_my_places, container, false);
        SharePlaceViewModel sharePlaceViewModel = new ViewModelProvider(requireActivity()).get(SharePlaceViewModel.class);
        sharePlaceViewModel.getPlaceId().observe(this,id -> {
            if(view.findViewById(R.id.place_detail) == null) {
                Intent intent = new Intent(requireActivity(), DetailPlace.class);
                intent.putExtra(DetailPlace.PLACE_ID,id);
                startActivity(intent);
            } else {
                DetailPlaceViewModel detailPlaceViewModel = new ViewModelProvider(requireActivity(),new ViewModelRepositoryFactory<DetailPlaceViewModel>(((CapstoneApplication)requireActivity().getApplication()).repository)).get(DetailPlaceViewModel.class);
                detailPlaceViewModel.setIdPlace(id);
            }
        });
        return view;
    }
}