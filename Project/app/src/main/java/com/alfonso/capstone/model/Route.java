package com.alfonso.capstone.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Route {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "routeId")
    private long idRoute;
    @ColumnInfo(name="name")
    private String name;

    public long getIdRoute() {
        return idRoute;
    }

    public void setIdRoute(long idRoute) {
        this.idRoute = idRoute;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
