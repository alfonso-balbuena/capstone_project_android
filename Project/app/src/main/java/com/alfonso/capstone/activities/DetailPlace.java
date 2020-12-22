package com.alfonso.capstone.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;

import com.alfonso.capstone.CapstoneApplication;
import com.alfonso.capstone.R;
import com.alfonso.capstone.databinding.ActivityDetailPlaceBinding;
import com.alfonso.capstone.databinding.ActivityMapsBinding;
import com.alfonso.capstone.viewmodel.DetailPlaceViewModel;
import com.alfonso.capstone.viewmodel.factory.ViewModelRepositoryFactory;

public class DetailPlace extends AppCompatActivity {

    public final static String PLACE_ID = "PLACE_ID";
    private ActivityDetailPlaceBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DetailPlaceViewModel viewModel = new ViewModelProvider(this,new ViewModelRepositoryFactory<DetailPlaceViewModel>(((CapstoneApplication)getApplication()).repository)).get(DetailPlaceViewModel.class);
        viewModel.setIdPlace(getIntent().getStringExtra(PLACE_ID));
        binding = ActivityDetailPlaceBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setSupportActionBar(binding.myToolbar);
        setContentView(view);
        setTitle(getString(R.string.detail));
    }
}