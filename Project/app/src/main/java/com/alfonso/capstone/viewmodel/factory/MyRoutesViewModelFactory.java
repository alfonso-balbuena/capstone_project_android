package com.alfonso.capstone.viewmodel.factory;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alfonso.capstone.repository.IRepository;
import com.alfonso.capstone.viewmodel.MyRoutesViewModel;

public class MyRoutesViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private IRepository repository;

    public MyRoutesViewModelFactory(IRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {

        return (T) new MyRoutesViewModel(repository);
    }
}
