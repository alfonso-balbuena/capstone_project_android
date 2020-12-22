package com.alfonso.capstone.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.alfonso.capstone.CapstoneApplication;
import com.alfonso.capstone.R;
import com.alfonso.capstone.databinding.ActivityDetailPlaceBinding;
import com.alfonso.capstone.databinding.ActivityRouteBinding;
import com.alfonso.capstone.viewmodel.RouteViewModel;
import com.alfonso.capstone.viewmodel.factory.ViewModelRepositoryFactory;

public class RouteActivity extends AppCompatActivity {

    public static final String ID_ROUTE = "ID_ROUTE";
    public static final String NAME_ROUTE = "NAME_ROUTE";
    private ActivityRouteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RouteViewModel viewModel = new ViewModelProvider(this,new ViewModelRepositoryFactory<RouteViewModel>(((CapstoneApplication)getApplication()).repository)).get(RouteViewModel.class);
        viewModel.setIdRoute(getIntent().getLongExtra(ID_ROUTE,1));
        binding = ActivityRouteBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setSupportActionBar(binding.myToolbar);
        setContentView(view);
        setTitle(getIntent().getStringExtra(NAME_ROUTE));
    }
}