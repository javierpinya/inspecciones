package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

import clh.inspecciones.com.inspecciones_v2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ControlAccesoCheckingFragment extends Fragment{

    private ListView mListView;
   // private Button btn;
    private dataListener callback;
    private TextView tv;

    public ControlAccesoCheckingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try{
            callback =(dataListener)context;
        }catch(Exception e){
            throw new ClassCastException(context.toString() + " should implement dataListener");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_control_acceso_checking, container, false);

     //   btn = (Button)view.findViewById(R.id.btnAInspecciones);

        mListView = (ListView)view.findViewById(R.id.lv_controlaccesochecking);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pulsada;
                pulsada = mListView.getItemAtPosition(position).toString();
                callback.itemPulsado(pulsada,position);
            }
        });

     //   btn.setOnClickListener(this);

        return  view;
    }


    public void renderText(List<String> datos) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, datos);
        mListView.setAdapter(adapter);
    }

    public interface dataListener{
        void itemPulsado(String vehiculo, int position);
        void datos_intent(List<String> datos);
        void inspecciones();
    }

}
