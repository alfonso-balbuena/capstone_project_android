package com.alfonso.capstone.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharePlaceViewModel extends ViewModel {

    private MutableLiveData<String> placeId;

    public SharePlaceViewModel() {
         placeId = new MutableLiveData<>();
    }

    public LiveData<String> getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId.setValue(placeId);
    }
}
