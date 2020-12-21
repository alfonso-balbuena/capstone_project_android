package com.alfonso.capstone.viewmodel;

import androidx.lifecycle.ViewModel;

import com.alfonso.capstone.repository.IRepository;

public class MapsLocationViewModel extends ViewModel {

    private IRepository repository;

    public MapsLocationViewModel(IRepository repository){
        this.repository = repository;
    }
}
