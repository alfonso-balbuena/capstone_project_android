package com.alfonso.capstone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.alfonso.capstone.databinding.ActivityMapsBinding;
import com.alfonso.capstone.fragments.MapsLocationFragment;
import com.alfonso.capstone.fragments.MyPlacesFragment;
import com.alfonso.capstone.fragments.MyRoutesFragment;
import com.google.android.libraries.places.api.Places;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends AppCompatActivity {


    private ActivityMapsBinding binding;
    private Map<Integer,Fragment> fragmentMap;
    private Map<Integer,String> titles;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        initMaps();
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            if(fragmentMap.containsKey(item.getItemId())) {
                setTitle(titles.get(item.getItemId()));
                changePageFragment(fragmentMap.get(item.getItemId()));
                return true;
            }
            return false;
        });
        setTitle(titles.get(R.id.home));
        changePageFragment(fragmentMap.get(R.id.home));
    }

    public void initMaps() {
        fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.home,new MapsLocationFragment());
        fragmentMap.put(R.id.places,new MyPlacesFragment());
        fragmentMap.put(R.id.routes, new MyRoutesFragment());
        titles = new HashMap<>();
        titles.put(R.id.home,getString(R.string.home));
        titles.put(R.id.routes,getString(R.string.my_routes));
        titles.put(R.id.places,getString(R.string.my_places));
    }

    private void changePageFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}