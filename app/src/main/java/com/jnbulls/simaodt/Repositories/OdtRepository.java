package com.jnbulls.simaodt.Repositories;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.jnbulls.simaodt.Data.DB.AppDatabase;
import com.jnbulls.simaodt.Data.DB.Dao.OdtDao;
import com.jnbulls.simaodt.Data.Entities.OdtEntity;

public class OdtRepository {
    private final OdtDao odtDao;
    private OdtEntity odtEntityLiveData;

    public OdtRepository(Context context){
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        odtDao = appDatabase.odtDao();
    }

    public void insertOdt(OdtEntity odtEntity){
        new InsertOdtAsyncTask(odtDao).execute(odtEntity);
    }

    public void deleteOdt(OdtEntity odtEntity){
        new DeleteOdtAsyncTask(odtDao).execute(odtEntity);
    }

    public void deleteAllOdt(){
        new DeleteAllOdtAsyncTask(odtDao).execute();
    }

    public OdtEntity getOdt(int odtNumber, String estado){
        Log.d("DAO", "Odt: "+odtNumber +" - Estado: "+ estado);
        odtEntityLiveData=odtDao.getOdt(odtNumber,estado);
        return odtEntityLiveData;
    }

    private static class InsertOdtAsyncTask extends AsyncTask<OdtEntity, Void, Void> {
        private final OdtDao odtDao;
        private InsertOdtAsyncTask(OdtDao odtDao) {
            this.odtDao = odtDao;
        }
        @Override
        protected Void doInBackground(OdtEntity... odtEntities) {
            odtDao.insert(odtEntities[0]);
            return null;
        }
    }

    private static class DeleteOdtAsyncTask extends AsyncTask<OdtEntity, Void, Void> {
        private final OdtDao odtDao;
        private DeleteOdtAsyncTask(OdtDao odtDao) {
            this.odtDao = odtDao;
        }
        @Override
        protected Void doInBackground(OdtEntity... odtEntities) {
            odtDao.delete(odtEntities[0]);
            return null;
        }
    }

    private static class DeleteAllOdtAsyncTask extends AsyncTask<Void, Void, Void> {
        private final OdtDao odtDao;
        private DeleteAllOdtAsyncTask(OdtDao odtDao) {
            this.odtDao = odtDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            odtDao.deleteAllOdt();
            return null;
        }
    }
}
