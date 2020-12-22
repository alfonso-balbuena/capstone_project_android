package com.alfonso.capstone.work;

import android.app.Notification;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationManagerCompat;
import androidx.room.Room;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.alfonso.capstone.R;
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
        Notification messageClearTables = new Notification.Builder(getApplicationContext())
                .setContentTitle(getApplicationContext().getString(R.string.note_notification))
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getApplicationContext());
        notificationManager.notify(0,messageClearTables);
        return Result.success();
    }
}
