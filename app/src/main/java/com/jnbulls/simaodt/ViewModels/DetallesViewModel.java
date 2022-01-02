package com.jnbulls.simaodt.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.jnbulls.simaodt.Data.Entities.DetallesOdtEntity;
import com.jnbulls.simaodt.Repositories.DetallesRepository;

public class DetallesViewModel extends ViewModel {
    private final DetallesRepository detallesRepository;
    private final MutableLiveData<DetallesOdtEntity> detallesOdtEntity;

    public DetallesViewModel(@NonNull Application application) {
        this.detallesRepository = new DetallesRepository(application);
        this.detallesOdtEntity= new MutableLiveData<>();
    }

    public void insertDetalle(DetallesOdtEntity detallesOdtEntity){
        detallesRepository.insertDetalle(detallesOdtEntity);
    }

    public void deleteDetalle(DetallesOdtEntity detallesOdtEntity){
        detallesRepository.deleteDetalle(detallesOdtEntity);
    }

    public void deleteAllDetalles(){
        detallesRepository.deleteAllDetalle();
    }

    public DetallesOdtEntity getDetalle(int numeroOdt){
        DetallesOdtEntity detallesOdtEntity = detallesRepository.getDetalle(numeroOdt);
        return detallesOdtEntity;
    }

    public void setDetallesOdtEntity(DetallesOdtEntity detallesOdt){
        this.detallesOdtEntity.setValue(detallesOdt);
    }

    public LiveData<DetallesOdtEntity> getDetallesOdtEntity(){
        return detallesOdtEntity;
    }

}
