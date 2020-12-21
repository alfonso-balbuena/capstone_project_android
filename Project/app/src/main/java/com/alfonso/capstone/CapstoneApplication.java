package com.alfonso.capstone;

import android.app.Application;

import androidx.room.Room;

import com.alfonso.capstone.database.RoutesDataBase;
import com.alfonso.capstone.repository.IRepository;
import com.alfonso.capstone.repository.imp.Repository;
import com.alfonso.capstone.services.imp.PlaceServiceGoogle;

public class CapstoneApplication extends Application {

    public IRepository repository;

    @Override
    public void onCreate() {
        super.onCreate();
        repository = new Repository(Room.databaseBuilder(this, RoutesDataBase.class,"database-routes").build(),new PlaceServiceGoogle(this));
    }
}
