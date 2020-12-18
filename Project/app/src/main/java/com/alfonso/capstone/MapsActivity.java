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
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends AppCompatActivity {

    private final int CODE_RESULT_PERMISSION = 1;
    private ActivityMapsBinding binding;
    private Map<Integer,Fragment> fragmentMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.home,new MapsLocationFragment());
        fragmentMap.put(R.id.places,new MyPlacesFragment());
        fragmentMap.put(R.id.routes, new MyRoutesFragment());
        binding.bottomNavigation.setOnNavigationItemSelectedListener(item -> {
            if(fragmentMap.containsKey(item.getItemId())) {
                changePageFragment(fragmentMap.get(item.getItemId()));
                return true;
            }
            return false;
        });
        changePageFragment(new MapsLocationFragment());
    }

    private void changePageFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void permission() {
        if(checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //Use geolocation
        } else {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},CODE_RESULT_PERMISSION);
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == CODE_RESULT_PERMISSION) {
            if(permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

            } else {
                AlertDialog alertDialog = new AlertDialog.Builder(this).setMessage(getString(R.string.permission_reason)).setTitle(getString(R.string.permission_dialog_title))
                        .setNeutralButton(R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).create();
                alertDialog.show();
            }
        }
    }
}