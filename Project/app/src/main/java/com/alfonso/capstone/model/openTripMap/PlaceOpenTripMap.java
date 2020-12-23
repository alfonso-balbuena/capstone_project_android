package com.alfonso.capstone.model.openTripMap;

import androidx.annotation.NonNull;

public class PlaceOpenTripMap {
    private String xid;
    private String name;
    private Address address;
    private String rate;
    private String image;
    private ExtractWiki wikipedia_extracts;
    private Point point;
    public String getXid() {
        return xid;
    }
    private String url;
    private Preview preview;

    public void setXid(String xid) {
        this.xid = xid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ExtractWiki getWikipedia_extracts() {
        return wikipedia_extracts;
    }

    public void setWikipedia_extracts(ExtractWiki wikipedia_extracts) {
        this.wikipedia_extracts = wikipedia_extracts;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Preview getPreview() {
        return preview;
    }

    public void setPreview(Preview preview) {
        this.preview = preview;
    }
}

class ExtractWiki {
    private String title;
    private String text;
    private String html;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }
}

class Point {
    private Double lon;
    private Double lat;

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }
}

class Preview {
    private String source;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}

class Address {
    private String city = "";
    private String house = "";
    private String state = "";
    private String county = "";
    private String country = "";
    private String postcode = "";
    private String pedestrian = "";
    private String neighbourhood = "";
    private String house_number = "";

    @NonNull
    @Override
    public String toString() {
        return pedestrian + " " + house_number +  " " + " " + neighbourhood + " " + postcode + " " + county + " " + city;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getPedestrian() {
        return pedestrian;
    }

    public void setPedestrian(String pedestrian) {
        this.pedestrian = pedestrian;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getHouse_number() {
        return house_number;
    }

    public void setHouse_number(String house_number) {
        this.house_number = house_number;
    }
}
