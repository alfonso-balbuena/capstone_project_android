package com.alfonso.capstone;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.alfonso.capstone.database.RoutesDataBase;
import com.alfonso.capstone.mock.MockPlaceService;
import com.alfonso.capstone.repository.imp.Repository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RepositoryInstrumentedTest {

    Repository repository;

    @Before
    public void init() {
        Context context = ApplicationProvider.getApplicationContext();
        RoutesDataBase dataBase = Room.inMemoryDatabaseBuilder(context, RoutesDataBase.class).allowMainThreadQueries().build();
        repository = new Repository(dataBase,new MockPlaceService());
    }




}
