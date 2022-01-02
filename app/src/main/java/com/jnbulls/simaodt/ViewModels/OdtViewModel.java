package com.jnbulls.simaodt.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.jnbulls.simaodt.Data.Entities.OdtEntity;
import com.jnbulls.simaodt.Repositories.OdtRepository;

public class OdtViewModel extends ViewModel {
    private final OdtRepository odtRepository;

    public OdtViewModel(@NonNull Application application) {
        this.odtRepository = new OdtRepository(application);
    }

    public void insertOdt(OdtEntity odtEntity){
        odtRepository.insertOdt(odtEntity);
    }

    public void deleteOdt(OdtEntity odtEntity){
        odtRepository.deleteOdt(odtEntity);
    }

    public void deleteAllOdt(){
        odtRepository.deleteAllOdt();
    }

    public OdtEntity getOdt(int numeroOdt, String estado){
        OdtEntity odtEntityLiveData = odtRepository.getOdt(numeroOdt, estado);
        return odtEntityLiveData;
    }
}
