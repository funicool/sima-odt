package com.jnbulls.simaodt.Workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.jnbulls.simaodt.Remote.DatosGeneralesApi;
import com.jnbulls.simaodt.Remote.Get.DatosFinales;
import com.jnbulls.simaodt.Remote.RetrofitClient;
import com.jnbulls.simaodt.Repositories.DataRepository;

import retrofit2.Call;
import retrofit2.Response;

public class DatosFinalesWorker extends Worker {

    private static final String TAG = DatosFinalesWorker.class.getSimpleName();
    private final DataRepository dataRepository;

    public DatosFinalesWorker(
        @NonNull Context appContext,
        @NonNull WorkerParameters workerParams) {
            super(appContext, workerParams);
            dataRepository=new DataRepository(appContext);
    }

    @NonNull
    @Override
    public Result doWork () {
        try{
            DatosGeneralesApi service = RetrofitClient.getRetrofitInstance().create(DatosGeneralesApi.class);
            Call<DatosFinales> call = service.getDatos();
            Response<DatosFinales> response = call.execute();
            if (response.isSuccessful() && response.body() != null) {
                Log.d(TAG, "Se eliminan todos los datos de la BD");
                dataRepository.deleteAll();
                Log.d(TAG, "Se inserta la respuesta de la API");
                dataRepository.insertAll(response);
                Log.i(TAG, "Result Success: " );
                return Result.success();
            } else {
                return Result.retry();
            }
        }catch (Exception e){
            e.printStackTrace();
            Log.i(TAG, "Result failure: " + e.getMessage());
            return Result.failure();
        }
    }

}
