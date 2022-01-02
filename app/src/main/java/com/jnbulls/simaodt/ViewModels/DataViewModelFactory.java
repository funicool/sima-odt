package com.jnbulls.simaodt.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DataViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;

    public DataViewModelFactory(Application app) {
        this.application = app;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DataViewModel.class))
            return (T) new DataViewModel(application);
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
