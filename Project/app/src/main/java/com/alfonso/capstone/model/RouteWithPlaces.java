package com.alfonso.capstone.model;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class RouteWithPlaces {
    @Embedded
    private Route route;

    @Relation(
            parentColumn = "routeId",
            entityColumn = "placeId",
            associateBy = @Junction(RoutePlaceCrossRef.class)
    )

    private List<PlaceCapstone> places;

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public List<PlaceCapstone> getPlaces() {
        return places;
    }

    public void setPlaces(List<PlaceCapstone> places) {
        this.places = places;
    }
}
