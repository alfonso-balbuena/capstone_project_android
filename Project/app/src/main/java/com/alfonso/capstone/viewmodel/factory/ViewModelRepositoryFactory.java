package com.alfonso.capstone.viewmodel.factory;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alfonso.capstone.repository.IRepository;
import java.lang.reflect.InvocationTargetException;

public class ViewModelRepositoryFactory<T extends ViewModel> extends ViewModelProvider.NewInstanceFactory {

    private final IRepository repository;

    public ViewModelRepositoryFactory(@NonNull IRepository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        try {
            return modelClass.getConstructor(IRepository.class).newInstance(repository);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


}
