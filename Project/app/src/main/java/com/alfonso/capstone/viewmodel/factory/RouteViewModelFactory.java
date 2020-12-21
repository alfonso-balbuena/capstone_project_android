package com.alfonso.capstone.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alfonso.capstone.repository.IRepository;
import com.alfonso.capstone.viewmodel.RouteViewModel;

public class RouteViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private final IRepository repository;

    public RouteViewModelFactory(IRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RouteViewModel(repository);
    }
}
