package com.alfonso.capstone.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfonso.capstone.CapstoneApplication;
import com.alfonso.capstone.activities.RouteActivity;
import com.alfonso.capstone.adapter.GenericAdapter;
import com.alfonso.capstone.adapter.RoutesAdapter;
import com.alfonso.capstone.databinding.FragmentMyRoutesBinding;
import com.alfonso.capstone.model.Route;
import com.alfonso.capstone.viewmodel.MyRoutesViewModel;
import com.alfonso.capstone.viewmodel.factory.MyRoutesViewModelFactory;

public class MyRoutesFragment extends Fragment implements AddRouteFragment.AddRouteDialogListener {

    private FragmentMyRoutesBinding binding;
    private MyRoutesViewModel viewModel;
    private GenericAdapter<Route> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentMyRoutesBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        viewModel = new ViewModelProvider(requireActivity(),new MyRoutesViewModelFactory(((CapstoneApplication)requireActivity().getApplication()).repository)).get(MyRoutesViewModel.class);
        binding.floatAddRoute.setOnClickListener(view1 -> {
            DialogFragment addRouteFragment = new AddRouteFragment(this);
            addRouteFragment.show(getFragmentManager(),"Add");
        });
        initRV();
        return view;
    }

    private void initRV() {
        binding.rvRoutes.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        binding.rvRoutes.setLayoutManager(layoutManager);
        adapter = new GenericAdapter<>(model -> {
           Intent intent = new Intent(requireActivity(), RouteActivity.class);
           intent.putExtra(RouteActivity.NAME_ROUTE,model.getName());
           intent.putExtra(RouteActivity.ID_ROUTE,model.getIdRoute());
           startActivity(intent);
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