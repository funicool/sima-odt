package com.jnbulls.simaodt.UI;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.ExistingWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.jnbulls.simaodt.Data.Entities.DetallesOdtEntity;
import com.jnbulls.simaodt.R;
import com.jnbulls.simaodt.ViewModels.DetallesViewModel;
import com.jnbulls.simaodt.ViewModels.DetallesViewModelFactory;
import com.jnbulls.simaodt.ViewModels.OdtViewModel;
import com.jnbulls.simaodt.Workers.DatosFinalesWorker;
import com.jnbulls.simaodt.Workers.DetalleOdtWorker;
import com.jnbulls.simaodt.Workers.OdtWorker;
import com.kofigyan.stateprogressbar.StateProgressBar;

import java.util.concurrent.TimeUnit;

import static com.jnbulls.simaodt.Utils.Constants.SYNC_DATA_WORK_NAME;
import static com.jnbulls.simaodt.Utils.Constants.SYNC_DATA_WORK_NAME_ODT;
import static com.jnbulls.simaodt.Utils.Constants.TAG_SYNC_DATA;
import static com.jnbulls.simaodt.Utils.Constants.TAG_SYNC_ODT;

/* Se ralizaron los siguientes cambios con respecto al producto entregado.
*  1 - Se actualizó el registro de materiales eliminados. Actualmente no se decrementan cuando se elimina del RecyclerView
*  2 - Se incorporó el exit de la app mediante el doble pulsado del boton Back
* */

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PassDataInterface {
    private static final String TAG= MainActivity.class.getSimpleName();
    private final String[] descriptionData = {"Situación\nInicial", "Reparación", "Materiales\nUtilizados", "Trabajo\nTerminado", "Informe\nFinal"};
    private StateProgressBar stateprogressbar;
    private Button aceptar, cancelar;
    private String estado="P1";
    private String reclamo;
    private int odt;
    private boolean band;
    private int usuario;
    //ViewModels
    private DetallesViewModel detallesViewModel;
    private OdtViewModel odtViewModel;
    //WorkManager
    private WorkManager mWorkManager;
    //Toast message Back Button
    private Toast backToast;
    private long backPressedTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        usuario = intent.getIntExtra("userId",0);
        mWorkManager = WorkManager.getInstance(this.getApplicationContext());
        //Inicializamos el Progress Bar y botones
        injectCommonViews();

        if (estado.equals("P1")){
            band=false;
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.contenedor_fragmento,InicialFragment.newInstance(estado))
                    .commit();
            stateprogressbar.setCurrentStateNumber(StateProgressBar.StateNumber.ONE);
        }

        /*OdtViewModelFactory odtViewModelFactory = new OdtViewModelFactory(this.getApplication());
        odtViewModel=new ViewModelProvider(this, odtViewModelFactory).get(OdtViewModel.class);*/

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.accept:
                if(!band){
                    Toast.makeText(this, "Debe sacar la Foto antes de Aceptar", Toast.LENGTH_LONG).show();
                    break;
                }else {
                    nextStep();
                    break;
                }
            case R.id.cancel:
                if(!band) {
                    Toast.makeText(this, "Debe sacar la Foto antes de Cancelar", Toast.LENGTH_LONG).show();
                    break;
                }else{
                        if((estado.equals("P1"))||(estado.equals("P2"))||(estado.equals("P7"))){
                            Toast.makeText(this, "No es posible cancelar una ODT en este paso",Toast.LENGTH_LONG).show();
                        }
                        else{
                            switch (estado){
                                case "P3": sendOdtData(odt, "P2");break;
                                case "P4": sendOdtData(odt, "P3");break;
                                case "F": sendOdtData(odt, "P4");break;
                            }
                            estado="C";
                            nextStep();
                        }
                    }
                break;
        }
    }

    private void injectCommonViews(){
        stateprogressbar = findViewById(R.id.usage_stateprogressbar);
        stateprogressbar.setStateDescriptionData(descriptionData);
        aceptar= findViewById(R.id.accept);
        aceptar.setOnClickListener(this);
        cancelar= findViewById(R.id.cancel);
        cancelar.setOnClickListener(this);
    }

    @Override
    public void onDataReceived(String estado, int odt, String reclamo, boolean bandera) {
        this.estado=estado;
        this.odt=odt;
        this.reclamo=reclamo;
        this.band=bandera;
    }

    void nextStep() {

        switch (estado) {
            case "P2":
                band=false;
                sendOdtData(odt,"P1");
                switchFragment(CapturadorFragment.newInstance(estado, odt, reclamo));
                stateprogressbar.setCurrentStateNumber(StateProgressBar.StateNumber.TWO);
                break;
            case "P3":
                band=false;
                sendOdtData(odt,"P2");
                switchFragment(CapturadorFragment.newInstance(estado, odt, reclamo));
                stateprogressbar.setCurrentStateNumber(StateProgressBar.StateNumber.THREE);
                break;
            case "P4":
                band=false;
                sendOdtData(odt,"P3");
                switchFragment(CapturadorFragment.newInstance(estado, odt, reclamo));
                stateprogressbar.setCurrentStateNumber(StateProgressBar.StateNumber.FOUR);
                break;
            case "C":
                switchFragment(FinalFragment.newInstance(estado, odt, reclamo));
                stateprogressbar.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
                break;
            case "F":
                sendOdtData(odt,"P4");
                switchFragment(FinalFragment.newInstance(estado, odt, reclamo));
                stateprogressbar.setCurrentStateNumber(StateProgressBar.StateNumber.FIVE);
                break;
            case "P7":
                DetallesViewModelFactory detallesViewModelFactory = new DetallesViewModelFactory(this.getApplication());
                detallesViewModel = new ViewModelProvider(this, detallesViewModelFactory).get(DetallesViewModel.class);
                detallesViewModel.getDetallesOdtEntity().observe(this, new Observer<DetallesOdtEntity>() {
                    @Override
                    public void onChanged(DetallesOdtEntity detallesOdtEntity) {
                        detallesViewModel.insertDetalle(detallesViewModel.getDetallesOdtEntity().getValue());
                    }
                });
                sendDetallesData(odt);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);

                LayoutInflater inflater = this.getLayoutInflater();
                builder.setView(inflater.inflate(R.layout.final_dialog,null));
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        intent.putExtra("userId",usuario);
                        finish();
                        startActivity(intent);
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
        }
    }

    public void switchFragment(Fragment fragment){
            getSupportFragmentManager().beginTransaction().
                    remove(getSupportFragmentManager().findFragmentById(R.id.contenedor_fragmento)).commit();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.contenedor_fragmento, fragment)
                    .commit();
    }

    public void sendOdtData(int odt, String state){
        Data.Builder data = new Data.Builder()
                .putInt("odtNumber", odt)
                .putString("estado", state);

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(OdtWorker.class)
                .addTag(TAG_SYNC_ODT)
                .setConstraints(constraints)
                .setInputData(data.build())
                // setting a backoff on case the work needs to retry. Retraso 10 segundos y política Lineal cada 10 se suman 10
                .setBackoffCriteria(BackoffPolicy.LINEAR, OneTimeWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                .build();

        mWorkManager.enqueueUniqueWork(
                SYNC_DATA_WORK_NAME_ODT + odt, //UniqueWork name
                ExistingWorkPolicy.APPEND, //Se agrega
                oneTimeWorkRequest
        );
    }

    public void sendDetallesData(int odt){
        Data.Builder data = new Data.Builder()
                .putInt("odtNumber", odt)
                .putInt("usuario",usuario);

        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest.Builder(DetalleOdtWorker.class)
                .addTag(TAG_SYNC_ODT)
                .setConstraints(constraints)
                .setInputData(data.build())
                // setting a backoff on case the work needs to retry. Retraso 10 segundos y política Lineal cada 10 se suman 10
                .setBackoffCriteria(BackoffPolicy.LINEAR, OneTimeWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                .build();

        mWorkManager.enqueueUniqueWork(
                SYNC_DATA_WORK_NAME_ODT + odt, //UniqueWork name
                ExistingWorkPolicy.APPEND, //Se agrega
                oneTimeWorkRequest
        );
    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()){
            backToast.cancel();
            super.onBackPressed();
            return;
        }else{
            backToast= Toast.makeText(getBaseContext(),"Presione nuevamente Back para salir", Toast.LENGTH_SHORT);
            backToast.show();
        }
        backPressedTime=System.currentTimeMillis();
    }
}
