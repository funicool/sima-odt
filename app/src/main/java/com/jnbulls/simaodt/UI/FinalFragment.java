package com.jnbulls.simaodt.UI;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jnbulls.simaodt.Data.Entities.DetallesOdtEntity;
import com.jnbulls.simaodt.Data.Entities.Informes;
import com.jnbulls.simaodt.Data.Entities.Materiales;
import com.jnbulls.simaodt.Data.Entities.Motivos;
import com.jnbulls.simaodt.Data.Entities.Zonas;
import com.jnbulls.simaodt.R;
import com.jnbulls.simaodt.UI.Adapters.MaterialesAdapter;
import com.jnbulls.simaodt.UI.Adapters.MaterialesItem;
import com.jnbulls.simaodt.ViewModels.DataViewModel;
import com.jnbulls.simaodt.ViewModels.DataViewModelFactory;
import com.jnbulls.simaodt.ViewModels.DetallesViewModel;
import com.jnbulls.simaodt.ViewModels.DetallesViewModelFactory;
import com.travijuu.numberpicker.library.NumberPicker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.jnbulls.simaodt.Utils.Constants.getDate;


public class FinalFragment extends Fragment {

    private static final String ARG_PARAM1 = "estado";
    private static final String ARG_PARAM2 = "odt";
    private static final String ARG_PARAM3 = "reclamo";
    private static final String TAG= FinalFragment.class.getSimpleName();
    //Variables
    private String estadoActual, estadoPosterior;
    private int odt;
    private String reclamo;
    private PassDataInterface passDataInterface;
    //vistas
    private Spinner zonaSpinner, motivoSpinner, informeSpinner, materialesSpinner;
    private NumberPicker numberPicker;
    private Button add;
    //RecyclerView
    private RecyclerView recyclerView;
    private MaterialesAdapter materialesAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private final ArrayList<MaterialesItem> materialesItems = new ArrayList<>();
    //viewmodel
    private DataViewModel dataViewModel;
    private DetallesViewModel detallesViewModel;
    //Detalle Odt
    private final DetallesOdtEntity detallesOdtEntity = new DetallesOdtEntity();
    //Materiales
    private Materiales materialesSelected;

    public FinalFragment() {
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

    public static FinalFragment newInstance(String param1, int param2, String param3) {
        FinalFragment fragment = new FinalFragment();
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
            odt = getArguments().getInt(ARG_PARAM2);
            reclamo = getArguments().getString(ARG_PARAM3);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_final, container, false);
        // Setea todos los Views del Fragment
        injectCommonViews(view);
        //Carga los Spinners con los datos de Room
        populateSpinners();
        //Inicializa Recycler View
        buildRecyclerView(view);
        //Muestra Materiales si no está cancelado
        LinearLayout materialesLayout= view.findViewById(R.id.materialesLayout);
        if(estadoActual.equals("F")){
            materialesLayout.setVisibility(View.VISIBLE);
            materialesSpinner.setVisibility(View.VISIBLE);
        }else {
            estadoActual="C";
            materialesLayout.setVisibility(View.GONE);
            materialesSpinner.setVisibility(View.GONE);
        }
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberPicker.getValue()!=0 && materialesSelected.getIdMateriales()!=-1){
                    InsertItem(materialesSelected.getIdMateriales(), materialesSelected.getDescripcion(), numberPicker.getValue());
                    numberPicker.setValue(0);
                }
                else{
                    Toast.makeText(getActivity(),"Debe ingresar al menos una cantidad y un material",Toast.LENGTH_LONG).show();
                }
            }
        });
        if (estadoPosterior==null) {
            seteaValoresDetalles();
            detallesViewModel.setDetallesOdtEntity(detallesOdtEntity);
        }
        passDataInterface.onDataReceived(estadoPosterior,odt,reclamo,true);

        Log.d(TAG, "Estado: " + detallesOdtEntity.getEstado());
        Log.d(TAG, "Materiales: " + detallesOdtEntity.getListaMateriales());
        Log.d(TAG, "Fecha: " + detallesOdtEntity.getFecha());
        return view;
    }

    public void injectCommonViews(View view){
        zonaSpinner = view.findViewById(R.id.zona_sp);
        motivoSpinner = view.findViewById(R.id.motivo_sp);
        informeSpinner = view.findViewById(R.id.informe_sp);
        materialesSpinner = view.findViewById(R.id.materiales_sp);
        numberPicker = view.findViewById(R.id.number_picker);
        add = view.findViewById(R.id.add);
    }

    public void buildRecyclerView(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        materialesAdapter = new MaterialesAdapter(materialesItems);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(materialesAdapter);

        materialesAdapter.setOnItemClickListener(new MaterialesAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                materialesItems.remove(position);
                materialesAdapter.notifyItemRemoved(position);
                detallesOdtEntity.deleteListaMateriales(position);
                Log.d("TAG", "Valor de Materiales: "+ detallesOdtEntity.getListaMateriales());
                detallesViewModel.setDetallesOdtEntity(detallesOdtEntity);
            }
        });
    }

    public void InsertItem(int id, String descripcion, int cantidad){
        materialesItems.add(new MaterialesItem(id,descripcion,cantidad));

        materialesAdapter.notifyDataSetChanged();
        materialesAdapter.notifyItemInserted(materialesItems.size());

        if ((detallesOdtEntity.getListaMateriales() == null)||(detallesOdtEntity.getListaMateriales() == "")) {
            detallesOdtEntity.setListaMateriales(new MaterialesItem(id,descripcion,cantidad).makeString());
        }else{
            detallesOdtEntity.addListaMateriales(new MaterialesItem(id,descripcion,cantidad).makeString());
        }
        detallesViewModel.setDetallesOdtEntity(detallesOdtEntity);
    }

    public void seteaValoresDetalles(){
        estadoPosterior="P7";
        DetallesViewModelFactory detallesViewModelFactory = new DetallesViewModelFactory(getActivity().getApplication());
        detallesViewModel = new ViewModelProvider(getActivity(), detallesViewModelFactory).get(DetallesViewModel.class);
        detallesOdtEntity.setNumeroOdt(odt);
        detallesOdtEntity.setEstado(estadoActual);
        detallesOdtEntity.setFecha(getDate("dd-MM-yyyy HH:mm:ss"));
    }

    public void populateSpinners(){
        DataViewModelFactory viewModelFactory = new DataViewModelFactory(getActivity().getApplication());
        dataViewModel = new ViewModelProvider(getActivity(), viewModelFactory).get(DataViewModel.class);

        final List<Zonas> listZonas = new ArrayList<>();
        listZonas.add(new Zonas(-1,"Seleccione Zona"));
        final ArrayAdapter adapterZonas = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, listZonas);
        adapterZonas.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataViewModel.getAllZonas().observe(this, new Observer<List<Zonas>>() {
            @Override
            public void onChanged(List<Zonas> zonas) {
                for(Zonas z : zonas ) {
                    listZonas.add(z);
                }
                adapterZonas.notifyDataSetChanged();
            }
        });
        zonaSpinner.setAdapter(adapterZonas);
        zonaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Zonas zonaSelected = (Zonas) adapterView.getItemAtPosition(i);
                detallesOdtEntity.setRelaZona(zonaSelected.getIdZonas());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final List<Motivos> listMotivos = new ArrayList<>();
        listMotivos.add(new Motivos(-1,"Seleccione Motivo"));
        final ArrayAdapter adapterMotivos = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, listMotivos);
        adapterMotivos.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataViewModel.getAllMotivos().observe(this, new Observer<List<Motivos>>() {
            @Override
            public void onChanged(List<Motivos> motivos) {
                for(Motivos z : motivos ) {
                    listMotivos.add(z);
                }
                adapterMotivos.notifyDataSetChanged();
            }
        });
        motivoSpinner.setAdapter(adapterMotivos);
        motivoSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Motivos motivosSelected = (Motivos) adapterView.getItemAtPosition(i);
                detallesOdtEntity.setRelaMotivo(motivosSelected.getIdMotivos());

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final List<Informes> listInformes = new ArrayList<>();
        listInformes.add(new Informes(-1,"Seleccione Informe"));
        final ArrayAdapter adapterInformes = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, listInformes);
        adapterInformes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataViewModel.getAllInformes().observe(this, new Observer<List<Informes>>() {
            @Override
            public void onChanged(List<Informes> informes) {
                for(Informes z : informes ) {
                    listInformes.add(z);
                }
                adapterInformes.notifyDataSetChanged();
            }
        });
        informeSpinner.setAdapter(adapterInformes);
        informeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Informes informeSelected = (Informes) adapterView.getItemAtPosition(i);
                detallesOdtEntity.setRelaInforme(informeSelected.getId());
                detallesViewModel.setDetallesOdtEntity(detallesOdtEntity);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final List<Materiales> listMateriales = new ArrayList<>();
        listMateriales.add(new Materiales(-1,"Seleccione Materiales"));
        final ArrayAdapter adapterMateriales = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_dropdown_item, listMateriales);
        adapterMateriales.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dataViewModel.getAllMateriales().observe(this, new Observer<List<Materiales>>() {
            @Override
            public void onChanged(List<Materiales> materiales) {
                for(Materiales z : materiales ) {
                    listMateriales.add(z);
                }
                adapterMateriales.notifyDataSetChanged();
            }
        });
        materialesSpinner.setAdapter(adapterMateriales);
        materialesSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                materialesSelected = (Materiales) adapterView.getItemAtPosition(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Log.d(TAG, "Zona: " + detallesOdtEntity.getRelaZona());
        Log.d(TAG, "Informe: " + detallesOdtEntity.getRelaInforme());
        Log.d(TAG, "Motivo: " + detallesOdtEntity.getRelaMotivo());

    }
}