package com.jnbulls.simaodt.UI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.jnbulls.simaodt.Data.Entities.Users;
import com.jnbulls.simaodt.R;
import com.jnbulls.simaodt.Repositories.DataRepository;
import com.jnbulls.simaodt.ViewModels.DataViewModel;
import com.jnbulls.simaodt.ViewModels.DataViewModelFactory;
import com.jnbulls.simaodt.Workers.DatosFinalesWorker;

import java.util.concurrent.TimeUnit;

import at.favre.lib.crypto.bcrypt.BCrypt;

import static com.jnbulls.simaodt.Utils.Constants.SYNC_DATA_WORK_NAME;
import static com.jnbulls.simaodt.Utils.Constants.TAG_SYNC_DATA;

public class LoginActivity extends AppCompatActivity {
    EditText et_username, et_password;
    Button btn_login;
    //WorkManager
    private WorkManager mWorkManager;
    private DataRepository dataRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        et_username = findViewById(R.id.userText);
        et_password = findViewById(R.id.passText);
        btn_login = findViewById(R.id.buttonLogin);
        mWorkManager = WorkManager.getInstance(this.getApplicationContext());
        fetchData();

        dataRepository=new DataRepository(getApplicationContext());
        /*DataViewModelFactory viewModelFactory = new DataViewModelFactory(this.getApplication());
        dataViewModel = new ViewModelProvider(this, viewModelFactory).get(DataViewModel.class);*/


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = et_username.getText().toString();
                String password = et_password.getText().toString();
                if (username.isEmpty() || password.isEmpty()){
                    Toast.makeText(view.getContext(), "Los campos no pueden estar vacíos", Toast.LENGTH_SHORT).show();
                }
                else{
                    Users usuario;
                    usuario=dataRepository.selectUser(username);
                    if (usuario!=null) {
                        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), usuario.getPassword());
                        if (result.verified) {
                            //Toast.makeText(view.getContext(), "Login Satisfactorio", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            intent.putExtra("userId", usuario.getIdUser());
                            startActivity(intent);
                        } else {
                            Toast.makeText(view.getContext(), "Password incorrecto", Toast.LENGTH_SHORT).show();
                        }
                    }else{Toast.makeText(view.getContext(), "Usuario no registrado", Toast.LENGTH_SHORT).show();}

                }
            }
        });
    }

    public void fetchData(){
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build();

        PeriodicWorkRequest periodicSyncDataWork =
                new PeriodicWorkRequest.Builder(DatosFinalesWorker.class, 30, TimeUnit.SECONDS)
                        .addTag(TAG_SYNC_DATA)
                        .setConstraints(constraints)
                        // setting a backoff on case the work needs to retry
                        .setBackoffCriteria(BackoffPolicy.LINEAR, PeriodicWorkRequest.MIN_BACKOFF_MILLIS, TimeUnit.MILLISECONDS)
                        .build();

        mWorkManager.enqueueUniquePeriodicWork(
                SYNC_DATA_WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP, //Existing Periodic Work policy
                periodicSyncDataWork //work request
        );
    }
}