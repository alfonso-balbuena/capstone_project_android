package com.alfonso.capstone.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"placeId","routeId"})
public class RoutePlaceCrossRef {
    @ColumnInfo(name = "placeId")
    @NonNull
    private String placeId;
    @ColumnInfo(name = "routeId")
    private long routeId;

    public RoutePlaceCrossRef() {
        placeId = "";
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public long getRouteId() {
        return routeId;
    }

    public void setRouteId(long routeId) {
        this.routeId = routeId;
    }
}
