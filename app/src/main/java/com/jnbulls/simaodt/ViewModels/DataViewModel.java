package com.jnbulls.simaodt.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.jnbulls.simaodt.Data.Entities.*;
import com.jnbulls.simaodt.Repositories.DataRepository;

import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private final DataRepository dataRepository;
    private final LiveData<List<Zonas>> allZonas;
    private final LiveData<List<Informes>> allInformes;
    private final LiveData<List<Materiales>> allMateriales;
    private final LiveData<List<Motivos>> allMotivos;
    private LiveData<Users> usuario;


    public DataViewModel (@NonNull Application application) {
        super(application);
        this.dataRepository = new DataRepository(application);
        allZonas=dataRepository.getAllZonas();
        allInformes=dataRepository.getAllInformes();
        allMateriales=dataRepository.getAllMateriales();
        allMotivos=dataRepository.getAllMotivos();
        //allUsers=dataRepository.getAllUsers();
    }

    public LiveData<List<Zonas>> getAllZonas(){
        return allZonas;
    }

    public LiveData<List<Informes>> getAllInformes() {
        return allInformes;
    }

    public LiveData<List<Materiales>> getAllMateriales() {
        return allMateriales;
    }

    public LiveData<List<Motivos>> getAllMotivos() {
        return allMotivos;
    }

    /*public Users getUserData(String email){
        Users usuario = dataRepository.getUser(email);
        return usuario;
    }*/
}
