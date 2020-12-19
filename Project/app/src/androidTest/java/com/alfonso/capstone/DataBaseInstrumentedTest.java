package com.alfonso.capstone;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.alfonso.capstone.database.RoutesDataBase;
import com.alfonso.capstone.model.PlaceCapstone;
import com.alfonso.capstone.model.Route;
import com.alfonso.capstone.model.RoutePlaceCrossRef;
import com.alfonso.capstone.model.RouteWithPlaces;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DataBaseInstrumentedTest {

    private RoutesDataBase dataBase;
    private final String PLACE_ID = "test";
    private final String NAME_ROUTE = "New route";

    @Before
    public void initDB() {
        Context context = ApplicationProvider.getApplicationContext();
        dataBase = Room.inMemoryDatabaseBuilder(context, RoutesDataBase.class).allowMainThreadQueries().build();
    }

    private PlaceCapstone makePlace() {
        PlaceCapstone placeTest = new PlaceCapstone();
        placeTest.setId(PLACE_ID);
        placeTest.setLatitude(23.3f);
        placeTest.setLongitude(23.3f);
        return placeTest;
    }

    private Route getRoute() {
        Route route = new Route();
        route.setName(NAME_ROUTE);
        return route;
    }

    @Test
    public void insertPlace() {
        dataBase.placeDao().insertPlace(makePlace());
        List<PlaceCapstone> aux = dataBase.placeDao().getAllPlaces();
        Assert.assertFalse(aux.isEmpty());
        Assert.assertEquals(PLACE_ID, aux.get(0).getId());
    }

    @Test
    public void insertRoute() {
        dataBase.routeDao().insert(getRoute());
        List<Route> routes = dataBase.routeDao().getAllRoutes();
        Assert.assertFalse(routes.isEmpty());
        Assert.assertEquals(NAME_ROUTE, routes.get(0).getName());
    }

    @Test
    public void insertCross() {
        dataBase.routeDao().insert(getRoute());
        dataBase.placeDao().insertPlace(makePlace());
        List<Route> routes = dataBase.routeDao().getAllRoutes();
        RoutePlaceCrossRef routePlaceCrossRef = new RoutePlaceCrossRef();
        routePlaceCrossRef.setPlaceId(PLACE_ID);
        routePlaceCrossRef.setRouteId(routes.get(0).getIdRoute());
        dataBase.placesRoutesDao().insertPlaceRoute(routePlaceCrossRef);
        RouteWithPlaces routeWithPlaces = dataBase.placesRoutesDao().getRouteWithPlace(routes.get(0).getIdRoute());
        Assert.assertEquals(routes.get(0).getIdRoute(),routeWithPlaces.getRoute().getIdRoute());
        Assert.assertFalse(routeWithPlaces.getPlaces().isEmpty());
    }

    @Test
    public void getPlaceIdNonInDataBase() {
        PlaceCapstone capstone = dataBase.placeDao().getPlaceById(PLACE_ID);
        Assert.assertNull(capstone);
    }
}
