package com.alfonso.capstone.contentProvider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Room;

import com.alfonso.capstone.database.RoutesDataBase;

import timber.log.Timber;

public class RouteContentProvider extends ContentProvider {

    public static final String URI_AUTHORITIES = "com.alfonso.capstone.provider";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + URI_AUTHORITIES);
    public static final String ROUTE_TABLE = "Route";
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private RoutesDataBase appDatabase;

    static {
        uriMatcher.addURI(URI_AUTHORITIES,ROUTE_TABLE,1);
    }



    @Override
    public boolean onCreate() {
        appDatabase = Room.databaseBuilder(getContext(),RoutesDataBase.class,RoutesDataBase.DATABASE_NAME).allowMainThreadQueries().build();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        Timber.d("Getting routes...");
        switch (uriMatcher.match(uri)) {
            case 1:
                return appDatabase.routeDao().getRoutes();
        }
        throw new IllegalArgumentException();
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        Timber.d("GetType");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Timber.d("Insert");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        Timber.d("Delete");
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        Timber.d("Update");
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
