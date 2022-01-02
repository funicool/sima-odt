package com.jnbulls.simaodt.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class DetallesViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;

    public DetallesViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DetallesViewModel.class))
            return (T) new DetallesViewModel(application);
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
