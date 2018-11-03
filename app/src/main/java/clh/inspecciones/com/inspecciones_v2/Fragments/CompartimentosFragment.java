package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import clh.inspecciones.com.inspecciones_v2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompartimentosFragment extends Fragment {

    /*
    El siguiente paso es cargar los compartimentos y transportista responsable en la consulta del camión. Así
    no repetimos otra búsqueda más. Así que habría que modificar el archivo de hostinger para incluir tplcprt y tpltrpt
     */


    public CompartimentosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_compartimentos, container, false);
    }

    public void renderCompartimentos(String matricula){

    }

    public interface dataListener{
        void enviarMatricula(String matricula);
    }

}
