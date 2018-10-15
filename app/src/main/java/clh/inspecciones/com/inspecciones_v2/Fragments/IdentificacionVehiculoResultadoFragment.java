package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

import clh.inspecciones.com.inspecciones_v2.Adapters.IdentificacionVehiculoAdapter;
import clh.inspecciones.com.inspecciones_v2.Clases.IdentificacionVehiculoClass;
import clh.inspecciones.com.inspecciones_v2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class IdentificacionVehiculoResultadoFragment extends Fragment {

    RecyclerView recyclerVehiculos;
    private IdentificacionVehiculoAdapter adapter;
    public EnviarData ed;

    public IdentificacionVehiculoResultadoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            ed = (IdentificacionVehiculoResultadoFragment.EnviarData)context;
        }catch(Exception e){
            throw new ClassCastException(context.toString() + " should implement EnviarData");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_identificacion_vehiculo_resultado, container, false);
        recyclerVehiculos = view.findViewById(R.id.rv_identificacionvehiculo);
        recyclerVehiculos.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerVehiculos.setHasFixedSize(true);


        return view;
    }

    public void renderText(ArrayList<IdentificacionVehiculoClass> listaVehiculos){

        adapter = new IdentificacionVehiculoAdapter(listaVehiculos, new IdentificacionVehiculoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(IdentificacionVehiculoClass identificacionVehiculoClass, int position) {
                String tractora;
                String cisterna;
                String conductor;
                tractora = identificacionVehiculoClass.getTractora().toString();
                cisterna = identificacionVehiculoClass.getCisterna().toString();
                conductor = identificacionVehiculoClass.getConductor().toString();
                ed.enviar(tractora, cisterna, conductor);
            }
        });
        recyclerVehiculos.setAdapter(adapter);
    }



    public interface EnviarData{
        void enviar(String tractora, String cisterna, String conductor);
    }


}

