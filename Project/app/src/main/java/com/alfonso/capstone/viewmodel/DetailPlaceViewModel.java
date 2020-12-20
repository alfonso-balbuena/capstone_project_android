package com.alfonso.capstone.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetailPlaceViewModel extends ViewModel {

    private final MutableLiveData<String> idPlace;

    public DetailPlaceViewModel() {
        idPlace = new MutableLiveData<>();
    }

    public LiveData<String> getIdPlace() {
        return idPlace;
    }

    public void setIdPlace(String idPlace) {
        this.idPlace.setValue(idPlace);
    }
}
