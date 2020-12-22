package com.alfonso.capstone.work;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.alfonso.capstone.database.RoutesDataBase;

import timber.log.Timber;

public class ClearDataBaseWorker extends Worker {

    private final RoutesDataBase dataBase;

    public ClearDataBaseWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        dataBase = Room.databaseBuilder(context, RoutesDataBase.class,RoutesDataBase.DATABASE_NAME).build();
    }

    @NonNull
    @Override
    public Result doWork() {
        Timber.d("Cleaning database...");
        dataBase.clearAllTables();
        return Result.success();
    }
}
