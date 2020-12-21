package com.alfonso.capstone.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alfonso.capstone.repository.IRepository;
import com.alfonso.capstone.viewmodel.DetailPlaceViewModel;

public class DetailPlaceViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final IRepository repository;

    public DetailPlaceViewModelFactory(IRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T) new DetailPlaceViewModel(repository);
    }

}
