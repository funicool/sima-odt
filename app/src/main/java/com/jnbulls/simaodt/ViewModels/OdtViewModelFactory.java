package com.jnbulls.simaodt.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class OdtViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;

    public OdtViewModelFactory (Application application){
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(OdtViewModel.class))
            return (T) new OdtViewModel(application);
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
