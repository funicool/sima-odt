package com.jnbulls.simaodt.Repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.jnbulls.simaodt.Data.DB.AppDatabase;
import com.jnbulls.simaodt.Data.DB.Dao.*;
import com.jnbulls.simaodt.Data.Entities.*;
import com.jnbulls.simaodt.Remote.Get.DatosFinales;

import java.util.List;
import java.util.concurrent.ExecutionException;

import retrofit2.Response;


public class DataRepository {
    private final InformesDao informesDao;
    private final MaterialesDao materialesDao;
    private final MotivosDao motivosDao;
    private final ZonasDao zonasDao;
    private final UsersDao usersDao;
    private final LiveData<List<Zonas>> allZonas;
    private final LiveData<List<Informes>> allInformes;
    private final LiveData<List<Materiales>> allMateriales;
    private final LiveData<List<Motivos>> allMotivos;
    private Users user;

    public DataRepository(Context applicationContext) {
        AppDatabase appDatabase = AppDatabase.getInstance(applicationContext);
        this.informesDao = appDatabase.informesDao();
        this.materialesDao = appDatabase.materialesDao();
        this.motivosDao = appDatabase.motivosDao();
        this.zonasDao = appDatabase.zonasDao();
        this.usersDao = appDatabase.usersDao();
        allInformes = informesDao.getAllInformes();
        allMateriales = materialesDao.getAllMateriales();
        allMotivos = motivosDao.getAllMotivos();
        allZonas = zonasDao.getAllZonas();
        //allUsers = usersDao.getAllUsers();

    }

    public void deleteAll() {
        informesDao.deleteAllInformes();
        materialesDao.deleteAllMateriales();
        motivosDao.deleteAllMotivos();
        zonasDao.deleteAllZonas();
        usersDao.deleteAllUsers();
    }

    public void insertAll(Response<DatosFinales> response) {
        informesDao.insert(response.body().getInformes());
        materialesDao.insert(response.body().getMateriales());
        motivosDao.insert(response.body().getMotivos());
        zonasDao.insert(response.body().getZonas());
        usersDao.insert(response.body().getUsers());
    }

    public LiveData<List<Zonas>> getAllZonas() {
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

    public Users selectUser(String email){
        Users user = null;
        try {
            user = new SelectUserAsyncTask(usersDao).execute(email).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }




    private static class DeleteAllDataAsyncTask extends AsyncTask<Void, Void, Void> {
        private final InformesDao informesDao;
        private final MaterialesDao materialesDao;
        private final MotivosDao motivosDao;
        private final ZonasDao zonasDao;
        private final UsersDao usersDao;

        public DeleteAllDataAsyncTask(InformesDao informesDao, MaterialesDao materialesDao, MotivosDao motivosDao, ZonasDao zonasDao, UsersDao usersDao){
            this.informesDao = informesDao;
            this.materialesDao = materialesDao;
            this.motivosDao = motivosDao;
            this.zonasDao = zonasDao;
            this.usersDao = usersDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            informesDao.deleteAllInformes();
            materialesDao.deleteAllMateriales();
            motivosDao.deleteAllMotivos();
            zonasDao.deleteAllZonas();
            usersDao.deleteAllUsers();
            return null;
        }
    }

    private static class SelectUserAsyncTask extends AsyncTask<String, Void, Users>{
        private final UsersDao usersDao;
        private SelectUserAsyncTask(UsersDao usersDao){
            this.usersDao=usersDao;
        }

        @Override
        protected Users doInBackground(String... strings) {
            Users user = usersDao.getUserByUsername(strings[0]);
            return user;
        }

        @Override
        protected void onPostExecute(Users users) {
            super.onPostExecute(users);
        }
    }

}
