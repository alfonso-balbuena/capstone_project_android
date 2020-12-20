package com.alfonso.capstone.fragments;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alfonso.capstone.CapstoneApplication;
import com.alfonso.capstone.R;
import com.alfonso.capstone.adapter.RoutesAdapter;
import com.alfonso.capstone.databinding.FragmentMapsLocationBinding;
import com.alfonso.capstone.databinding.FragmentMyRoutesBinding;
import com.alfonso.capstone.services.imp.PlaceServiceGoogle;
import com.alfonso.capstone.viewmodel.MyRoutesViewModel;
import com.alfonso.capstone.viewmodel.MyRoutesViewModelFactory;
import com.google.android.gms.location.LocationServices;

public class MyRoutesFragment extends Fragment implements AddRouteFragment.AddRouteDialogListener {

    private FragmentMyRoutesBinding binding;
    private MyRoutesViewModel viewModel;
    private RoutesAdapter adapter;

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
        adapter = new RoutesAdapter(model -> {
           Log.d("ROUTEs", "Click en " + model.getIdRoute() + "  --- " + model.getName());
        });
        binding.rvRoutes.setAdapter(adapter);

        viewModel.getAllRoutes().observe(this,routes -> {
            adapter.setRouteList(routes);
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