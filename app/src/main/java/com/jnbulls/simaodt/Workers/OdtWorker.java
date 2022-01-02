package com.jnbulls.simaodt.Workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.jnbulls.simaodt.Data.Entities.OdtEntity;
import com.jnbulls.simaodt.Remote.DatosGeneralesApi;
import com.jnbulls.simaodt.Remote.RetrofitClient;
import com.jnbulls.simaodt.Repositories.OdtRepository;

import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class OdtWorker extends Worker {
    private static final String TAG = OdtWorker.class.getSimpleName();
    private final OdtRepository odtRepository;
    private int idOdt;

    public OdtWorker(
            @NonNull Context appContext,
            @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
        odtRepository=new OdtRepository(appContext);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            //Obtengo los datos pasados por parámetro
            int odtAux = getInputData().getInt("odtNumber", 0);
            String estadoAux = getInputData().getString("estado");
            OdtEntity odtEntity= odtRepository.getOdt(odtAux,estadoAux);

            //Genero los datos de Subida
            RequestBody reclamo=RequestBody.create(MediaType.parse("text/plain"),odtEntity.getNumeroReclamo());
            RequestBody estado=RequestBody.create(MediaType.parse("text/plain"),odtEntity.getEstado());
            RequestBody fecha=RequestBody.create(MediaType.parse("text/plain"),odtEntity.getFecha());
            File imageFile = new File(odtEntity.getFotoUrl());
            RequestBody requestFile = RequestBody.create(MediaType.parse("application/octet-stream"),imageFile);
            MultipartBody.Part body = MultipartBody.Part.createFormData("image",imageFile.getName(),requestFile);
            String tipo="multipart/form-data";

            //Almaceno el id de la Base para su posterior borrado
            idOdt=odtEntity.getId();

            //Genero el servicio
            DatosGeneralesApi service = RetrofitClient.getRetrofitInstance().create(DatosGeneralesApi.class);
            Response<ResponseBody> response = service.uploadOdt(tipo,body,odtAux,estado,reclamo,fecha).execute();

            if (response.isSuccessful() && response.body() != null) {
                JSONObject jobj = new JSONObject(response.body().string());
                Log.d("EXITO!","Message: "+jobj.toString());
                odtRepository.deleteOdt(odtEntity);

                Log.i(TAG, "Result Success " );
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
