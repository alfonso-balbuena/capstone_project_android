package com.alfonso.capstone;

import android.app.Application;

import androidx.room.Room;

import com.alfonso.capstone.database.RoutesDataBase;
import com.alfonso.capstone.repository.IRepository;
import com.alfonso.capstone.repository.imp.Repository;
import com.alfonso.capstone.repository.imp.RepositoryOpenTripMap;
import com.alfonso.capstone.services.imp.OpenTripMapService;
import com.alfonso.capstone.services.imp.PlaceServiceGoogle;

import timber.log.Timber;

public class CapstoneApplication extends Application {

    public IRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        repository = new RepositoryOpenTripMap(Room.databaseBuilder(this, RoutesDataBase.class,RoutesDataBase.DATABASE_NAME).build(),new OpenTripMapService(this));
        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
