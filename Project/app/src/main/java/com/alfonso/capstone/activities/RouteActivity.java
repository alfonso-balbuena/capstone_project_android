package com.alfonso.capstone.activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.alfonso.capstone.CapstoneApplication;
import com.alfonso.capstone.R;
import com.alfonso.capstone.viewmodel.RouteViewModel;
import com.alfonso.capstone.viewmodel.factory.RouteViewModelFactory;

public class RouteActivity extends AppCompatActivity {

    public static final String ID_ROUTE = "ID_ROUTE";
    public static final String NAME_ROUTE = "NAME_ROUTE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RouteViewModel viewModel = new ViewModelProvider(this,new RouteViewModelFactory(((CapstoneApplication)getApplication()).repository)).get(RouteViewModel.class);
        viewModel.setIdRoute(getIntent().getLongExtra(ID_ROUTE,1));
        setTitle(getIntent().getStringExtra(NAME_ROUTE));
        setContentView(R.layout.activity_route);
    }
}