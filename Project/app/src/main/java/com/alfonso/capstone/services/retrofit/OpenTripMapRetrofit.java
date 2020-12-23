package com.alfonso.capstone.services.retrofit;

import com.alfonso.capstone.model.openTripMap.Feature;
import com.alfonso.capstone.model.openTripMap.FeatureRequest;
import com.alfonso.capstone.model.openTripMap.PlaceOpenTripMap;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OpenTripMapRetrofit {

    @GET("{lang}/places/xid/{xid}")
    Call<PlaceOpenTripMap> getDetail(@Path("lang")String lang,@Path("xid") String xid, @Query("apikey") String key);

    @GET("{lang}/places/radius")
    Call<FeatureRequest> getPlaceForLocation(@Path("lang")String lang, @Query("radius")Long radius, @Query("lon")Double lon, @Query("lat")Double lat, @Query("apikey") String key);

}
