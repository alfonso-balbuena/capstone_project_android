package com.alfonso.capstone.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

import java.util.Objects;

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

    @Ignore
    public RoutePlaceCrossRef(long routeId,String placeId) {
        Objects.nonNull(placeId);
        this.routeId = routeId;
        this.placeId = placeId;
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
