package com.alfonso.capstone.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedRouteViewModel extends ViewModel {
    private MutableLiveData<Long> routeId;
    private MutableLiveData<String> name;

    public SharedRouteViewModel() {
        routeId = new MutableLiveData<>();
        name = new MutableLiveData<>();
    }

    public void selectRoute(Long routeId,String name) {
        setName(name);
        setRouteId(routeId);
    }

    public LiveData<Long> getRouteId() {
        return routeId;
    }

    public void setRouteId(Long routeId) {
        this.routeId.setValue(routeId);;
    }

    public LiveData<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }
}
