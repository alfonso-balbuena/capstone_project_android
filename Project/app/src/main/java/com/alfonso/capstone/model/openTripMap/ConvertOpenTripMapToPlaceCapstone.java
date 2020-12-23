package com.alfonso.capstone.model.openTripMap;

import android.net.Uri;

import com.alfonso.capstone.model.PlaceCapstone;

import java.net.URI;
import java.net.URISyntaxException;

public class ConvertOpenTripMapToPlaceCapstone {
    public static PlaceCapstone convertToPlacesCapstone(Feature feature) {
        PlaceCapstone placeCapstone = new PlaceCapstone();
        placeCapstone.setId(feature.getProperties().getXid());
        placeCapstone.setName(feature.getProperties().getName());
        placeCapstone.setLongitude(feature.getGeometry().getCoordinates().get(0));
        placeCapstone.setLatitude(feature.getGeometry().getCoordinates().get(1));
        return placeCapstone;
    }

    public static PlaceCapstone convertToPlaceCapstone(PlaceOpenTripMap openTripMap) {
        PlaceCapstone placeCapstone = new PlaceCapstone();
        placeCapstone.setId(openTripMap.getXid());
        placeCapstone.setName(openTripMap.getName());
        placeCapstone.setLatitude(openTripMap.getPoint().getLat());
        placeCapstone.setLongitude(openTripMap.getPoint().getLon());
        placeCapstone.setRating(openTripMap.getRate());
        placeCapstone.setAddress(openTripMap.getAddress().toString());
        if(openTripMap.getUrl() != null) {
            placeCapstone.setWebsite(Uri.parse(openTripMap.getUrl()));
        }
        if(openTripMap.getPreview() != null) {
            placeCapstone.setImage(openTripMap.getPreview().getSource());
        }
        return placeCapstone;
    }
}
