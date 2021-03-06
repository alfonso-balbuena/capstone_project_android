package com.alfonso.capstone.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfonso.capstone.CapstoneApplication;
import com.alfonso.capstone.R;
import com.alfonso.capstone.activities.RouteActivity;
import com.alfonso.capstone.viewmodel.RouteViewModel;
import com.alfonso.capstone.viewmodel.SharedRouteViewModel;
import com.alfonso.capstone.viewmodel.factory.ViewModelRepositoryFactory;

import timber.log.Timber;

public class MainMyRoutesFragment extends Fragment {

    private SharedRouteViewModel sharedRouteViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        sharedRouteViewModel = new ViewModelProvider(requireActivity()).get(SharedRouteViewModel.class);
        View view = inflater.inflate(R.layout.fragment_main_my_routes, container, false);
        sharedRouteViewModel.getRouteId().observe(this,id -> {
            Timber.d("Navigating to " + id + sharedRouteViewModel.getName().getValue());
            if(id > 0) {
                if(view.findViewById(R.id.route_detail) == null) {
                    Intent intent = new Intent(requireActivity(), RouteActivity.class);
                    intent.putExtra(RouteActivity.NAME_ROUTE,sharedRouteViewModel.getName().getValue());
                    intent.putExtra(RouteActivity.ID_ROUTE,sharedRouteViewModel.getRouteId().getValue());
                    startActivity(intent);
                    Timber.d("Starting activity");
                } else  {
                    RouteViewModel viewModelShowRoute = new ViewModelProvider(requireActivity(),new ViewModelRepositoryFactory<RouteViewModel>(((CapstoneApplication)requireActivity().getApplication()).repository)).get(RouteViewModel.class);
                    viewModelShowRoute.setIdRoute(id);
                }
                sharedRouteViewModel.selectRoute(-1L,"");
            }
        });
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        sharedRouteViewModel.selectRoute(-1L,"");
        Timber.d("Setting id to %s", sharedRouteViewModel.getRouteId().getValue());
    }
}