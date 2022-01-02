package com.jnbulls.simaodt.Repositories;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.jnbulls.simaodt.Data.DB.AppDatabase;
import com.jnbulls.simaodt.Data.DB.Dao.DetallesOdtDao;
import com.jnbulls.simaodt.Data.Entities.DetallesOdtEntity;

public class DetallesRepository {
    private final DetallesOdtDao detallesOdtDao;

    public DetallesRepository(Context context){
        AppDatabase appDatabase = AppDatabase.getInstance(context);
        detallesOdtDao = appDatabase.detallesOdtDao();
    }

    public void insertDetalle(DetallesOdtEntity detallesOdtEntity){
        new InsertDetalleAsyncTask(detallesOdtDao).execute(detallesOdtEntity);
    }

    public void deleteDetalle(DetallesOdtEntity detallesOdtEntity){
        new DeleteDetalleAsyncTask(detallesOdtDao).execute(detallesOdtEntity);
    }

    public void deleteAllDetalle(){
        new DeleteAllDetalleAsyncTask(detallesOdtDao).execute();
    }

    public DetallesOdtEntity getDetalle(int odt){
        return detallesOdtDao.getDetalleOdt(odt);
    }

    private static class InsertDetalleAsyncTask extends AsyncTask<DetallesOdtEntity, Void, Void> {
        private final DetallesOdtDao detallesOdtDao;
        private InsertDetalleAsyncTask(DetallesOdtDao detallesOdtDao) {
            this.detallesOdtDao = detallesOdtDao;
        }
        @Override
        protected Void doInBackground(DetallesOdtEntity... detallesOdtEntities) {
            detallesOdtDao.insert(detallesOdtEntities[0]);
            return null;
        }
    }

    private static class DeleteDetalleAsyncTask extends AsyncTask<DetallesOdtEntity, Void, Void> {
        private final DetallesOdtDao detallesOdtDao;
        private DeleteDetalleAsyncTask(DetallesOdtDao detallesOdtDao) {
            this.detallesOdtDao = detallesOdtDao;
        }
        @Override
        protected Void doInBackground(DetallesOdtEntity... detallesOdtEntities) {
            detallesOdtDao.delete(detallesOdtEntities[0]);
            return null;
        }
    }

    private static class DeleteAllDetalleAsyncTask extends AsyncTask<Void, Void, Void> {
        private final DetallesOdtDao detallesOdtDao;
        private DeleteAllDetalleAsyncTask(DetallesOdtDao detallesOdtDao) {
            this.detallesOdtDao = detallesOdtDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            detallesOdtDao.deleteAllDetallesOdt();
            return null;
        }
    }
}
