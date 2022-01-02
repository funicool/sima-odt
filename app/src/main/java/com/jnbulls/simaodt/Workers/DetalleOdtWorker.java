package com.jnbulls.simaodt.Workers;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.jnbulls.simaodt.Data.Entities.DetallesOdtEntity;
import com.jnbulls.simaodt.Remote.DatosGeneralesApi;
import com.jnbulls.simaodt.Remote.RetrofitClient;
import com.jnbulls.simaodt.Repositories.DetallesRepository;
import com.jnbulls.simaodt.Utils.WorkerUtils;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class DetalleOdtWorker extends Worker {
    private static final String TAG = DetalleOdtWorker.class.getSimpleName();
    private final DetallesRepository detallesRepository;


    public DetalleOdtWorker(
            @NonNull Context appContext,
            @NonNull WorkerParameters workerParams) {
        super(appContext, workerParams);
        detallesRepository=new DetallesRepository(appContext);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            //Obtengo los datos pasados por parámetro
            int odtAux = getInputData().getInt("odtNumber", 0);
            int usuario = getInputData().getInt("usuario",0);

            DetallesOdtEntity detallesOdtEntity= detallesRepository.getDetalle(odtAux);

            //Genero los datos de Subida
            RequestBody materiales, relaInforme, relaMotivo;

            //Si no se carga la Zona, por defecto irá a Capital o Caucete (depende del teléfono)
            int relaZona = 2;

            //Si los datos no fueron cargados enviar el valor por defecto
            if(detallesOdtEntity.getListaMateriales()==null)
               materiales=RequestBody.create(MediaType.parse("text/plain"), "");
            else
                materiales=RequestBody.create(MediaType.parse("text/plain"),detallesOdtEntity.getListaMateriales());

            if(detallesOdtEntity.getRelaInforme()==-1)
                relaInforme=RequestBody.create(MediaType.parse("text/plain"),"");
            else
                relaInforme=RequestBody.create(MediaType.parse("text/plain"),String.valueOf(detallesOdtEntity.getRelaInforme()));

            if(detallesOdtEntity.getRelaMotivo()==-1)
                relaMotivo=RequestBody.create(MediaType.parse("text/plain"),"");
            else
                relaMotivo=RequestBody.create(MediaType.parse("text/plain"),String.valueOf(detallesOdtEntity.getRelaMotivo()));

            if (detallesOdtEntity.getRelaZona() != -1)
                relaZona = detallesOdtEntity.getRelaZona();

            RequestBody estado=RequestBody.create(MediaType.parse("text/plain"),detallesOdtEntity.getEstado());

            String tipo="multipart/form-data";

            //Genero el servicio
            DatosGeneralesApi service = RetrofitClient.getRetrofitInstance().create(DatosGeneralesApi.class);

            Response<ResponseBody> response = service.actualizaOdt(tipo,relaZona,relaInforme,relaMotivo,usuario,odtAux, materiales, estado)
                    .execute();

            if (response.isSuccessful() && response.body() != null) {
                JSONObject jobj = new JSONObject(response.body().string());
                Log.d("EXITO!","Message: "+jobj.toString());
                detallesRepository.deleteDetalle(detallesOdtEntity);
                WorkerUtils.makeStatusNotification("Se terminó de subir la Orden de Trabajo: "+odtAux, getApplicationContext());
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
