package com.alfonso.capstone.model;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.android.libraries.places.api.model.PhotoMetadata;
import com.google.android.libraries.places.api.model.Place;

import java.util.List;

@Entity
public class PlaceCapstone {
    @PrimaryKey
    @ColumnInfo(name = "placeId")
    @NonNull
    private String id;
    @ColumnInfo(name = "latitude")
    private double latitude;
    @ColumnInfo(name = "longitude")
    private double longitude;
    @ColumnInfo(name = "name")
    private String name;
    @Ignore
    private String address;
    @Ignore
    private Double rating;
    @Ignore
    private String phone;
    @Ignore
    private Uri website;
    @Ignore
    private List<PhotoMetadata> photoMetadataList;
    @Ignore
    private String image = "";


    public PlaceCapstone() {
        id = "";
    }

    public PlaceCapstone(String id, String name, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(String rating) {
        double aux = 0D;
        if(rating.contains("h"))
            aux += .5;
        this.rating = Double.parseDouble(rating.substring(0,1)) + aux;
    }

    @NonNull
    @Override
    public String toString() {
        return name;
    }


    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Uri getWebsite() {
        return website;
    }

    public void setWebsite(Uri website) {
        this.website = website;
    }

    public List<PhotoMetadata> getPhotoMetadataList() {
        return photoMetadataList;
    }

    public void setPhotoMetadataList(List<PhotoMetadata> photoMetadataList) {
        this.photoMetadataList = photoMetadataList;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
