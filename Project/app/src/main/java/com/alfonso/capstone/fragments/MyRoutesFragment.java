package com.alfonso.capstone.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfonso.capstone.CapstoneApplication;
import com.alfonso.capstone.activities.RouteActivity;
import com.alfonso.capstone.adapter.GenericAdapter;
import com.alfonso.capstone.databinding.FragmentMyRoutesBinding;
import com.alfonso.capstone.model.Route;
import com.alfonso.capstone.viewmodel.MyRoutesViewModel;
import com.alfonso.capstone.viewmodel.SharedRouteViewModel;
import com.alfonso.capstone.viewmodel.factory.ViewModelRepositoryFactory;

public class MyRoutesFragment extends Fragment implements AddRouteFragment.AddRouteDialogListener {

    private FragmentMyRoutesBinding binding;
    private MyRoutesViewModel viewModel;
    private GenericAdapter<Route> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMyRoutesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        viewModel = new ViewModelProvider(requireActivity(),new ViewModelRepositoryFactory<MyRoutesViewModel>(((CapstoneApplication)requireActivity().getApplication()).repository)).get(MyRoutesViewModel.class);

        binding.floatAddRoute.setOnClickListener(view1 -> {
            DialogFragment addRouteFragment = new AddRouteFragment(this);
            addRouteFragment.show(getFragmentManager(),"Add");
        });
        initRV();
        return view;
    }

    private void initRV() {
        SharedRouteViewModel sharedRouteViewModel = new ViewModelProvider(requireActivity()).get(SharedRouteViewModel.class);
        binding.rvRoutes.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvRoutes.setLayoutManager(layoutManager);
        adapter = new GenericAdapter<>(model -> {
            sharedRouteViewModel.selectRoute(model.getIdRoute(),model.getName());
        });
        binding.rvRoutes.setAdapter(adapter);
        viewModel.getAllRoutes().observe(this,routes -> {
            adapter.setList(routes);
        });
    }


    @Override
    public void onDialogOkClick(DialogFragment dialog, String routeName) {
        viewModel.addRoute(routeName);
        dialog.dismiss();
    }

    @Override
    public void onDialogCancelClick(DialogFragment dialog) {
        dialog.dismiss();
    }
}