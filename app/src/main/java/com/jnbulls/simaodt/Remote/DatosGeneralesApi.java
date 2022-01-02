package com.jnbulls.simaodt.Remote;

import com.jnbulls.simaodt.Remote.Get.*;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface DatosGeneralesApi {

    @GET("datos")
    Call<DatosFinales> getDatos();

    @Multipart
    @POST("foto")
    Call<ResponseBody> uploadOdt(@Header("enctype") String tipo,
                                 @Part MultipartBody.Part image,
                                 @Part("orden") int orden,
                                 @Part("estado") RequestBody estado,
                                 @Part("reclamo") RequestBody reclamo,
                                 @Part("fecha") RequestBody fecha);

    @Multipart
    @POST("actualiza")
    Call<ResponseBody> actualizaOdt(@Header("enctype") String tipo,
                                    @Part("idzona") int zona,
                                    @Part("idinforme") RequestBody informe,
                                    @Part("idmotivoreparacion") RequestBody motivos,
                                    @Part("idusuario") int usuario,
                                    @Part("orden") int orden,
                                    @Part("idmaterial") RequestBody materiales,
                                    @Part("codigo") RequestBody estado);
}
