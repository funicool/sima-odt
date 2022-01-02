package com.jnbulls.simaodt.UI;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.jnbulls.simaodt.Data.Entities.OdtEntity;
import com.jnbulls.simaodt.R;
import com.jnbulls.simaodt.Utils.ImageCompressionAsyncTask;
import com.jnbulls.simaodt.ViewModels.OdtViewModel;
import com.jnbulls.simaodt.ViewModels.OdtViewModelFactory;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;
import static com.jnbulls.simaodt.Utils.Constants.getDate;

public class CapturadorFragment extends Fragment {

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "estado";
    private static final String ARG_PARAM2 = "odt";
    private static final String ARG_PARAM3 = "reclamo";
    private static final int IMAGE_CAPTURE_CODE = 1;
    private static final String TAG= CapturadorFragment.class.getSimpleName();
    //Vistas
    private ImageButton capturador;
    //ViewModel
    private OdtViewModel odtViewModel;
    //Variables
    private String estadoActual, estadoPosterior, currentPhotoPath, ahora;
    private int odtNumber;
    private String reclamo;
    private PassDataInterface passDataInterface;



    public CapturadorFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof PassDataInterface) {
            passDataInterface = (PassDataInterface) context;
        } else throw new ClassCastException(context.toString()
                + getResources().getString(R.string.exception_message));
    }

    public static CapturadorFragment newInstance(String param1, int param2, String param3) {
        CapturadorFragment fragment = new CapturadorFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putInt(ARG_PARAM2, param2);
        args.putString(ARG_PARAM3, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            estadoActual = getArguments().getString(ARG_PARAM1);
            odtNumber = getArguments().getInt(ARG_PARAM2);
            reclamo = getArguments().getString(ARG_PARAM3);
            ahora=getDate("dd-MM-yyyy HH:mm:ss");
            }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_capturador, container, false);
        injectCommonViews(view);
        Log.d(TAG, "Los datos son: "+ estadoActual +" "+ odtNumber +" "+ reclamo);
        capturador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Registra el estado posterior que se enviará al MainActivity
                switch (estadoActual) {
                    case "P2":
                        estadoPosterior = "P3";
                        break;
                    case "P3":
                        estadoPosterior = "P4";
                        break;
                    case "P4":
                        estadoPosterior = "F";
                        break;
                }
                //Genera el archivo para almacenar la foto e inicia la Cámara
                Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if ((getActivity() != null) && (cInt.resolveActivity(getActivity().getPackageManager()) != null)) {
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        Log.d(TAG, "EXCEPCION: " + ex.getMessage());
                    }
                    // Continúa solo si el archivo ha sido creado
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getActivity().getApplicationContext(),
                                "com.jnbulls.simaodt.fileprovider",
                                photoFile);
                        cInt.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(cInt, IMAGE_CAPTURE_CODE);
                    }
                }
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_CAPTURE_CODE) {
            if (resultCode == RESULT_OK) {
                try {
                    //Muestra un Dialog durante 2 segundos para esperar a que se genere la foto
                    final LoadingDialog loadingDialog = new LoadingDialog(getActivity());
                    loadingDialog.startLoadingDialog();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loadingDialog.dismissDialog();
                        }
                    }, 2000);

                    File f = new File(currentPhotoPath);
                    final Uri contentUri = Uri.fromFile(f);
                    ImageCompressionAsyncTask imageCompression=new ImageCompressionAsyncTask() {
                        @Override
                        public void onPostExecute(byte[] imageBytes) {
                            Picasso.get()
                                    .load(contentUri)
                                    .resize(300, 300)
                                    .centerCrop()
                                    .into(capturador);
                        }
                    };
                    imageCompression.execute(currentPhotoPath);// imagePath as a string

                    OdtEntity odt=new OdtEntity(odtNumber,reclamo,currentPhotoPath,ahora,estadoActual);
                    OdtViewModelFactory odtViewModelFactory = new OdtViewModelFactory(getActivity().getApplication());
                    odtViewModel=new ViewModelProvider(getActivity(),odtViewModelFactory).get(OdtViewModel.class);
                    Log.d(TAG,"En este punto inserta la ODT");
                    odtViewModel.insertOdt(odt);
                    passDataInterface.onDataReceived(estadoPosterior,odtNumber,reclamo,true);
                }catch (Exception e){
                    Log.d(TAG, "Error al generar la imagen: " + e.getMessage());
                }

            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(getContext(), "Cancelado", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void injectCommonViews(View view){
        capturador=view.findViewById(R.id.imageButton);
    }

    public File createImageFile() throws IOException{
        // Create an image file name
        String timeStamp = getDate("yyyyMMdd_HHmmss");
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getFilesDir();
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath=image.getAbsolutePath();
        return image;
    }
}