package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import java.util.ArrayList;
import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Adapters.VerCompartimentosAdapter;
import clh.inspecciones.com.inspecciones_v2.Clases.CACompartimentosBD;
import clh.inspecciones.com.inspecciones_v2.R;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerCompartimentosFragment extends Fragment implements RealmChangeListener<RealmResults<CACompartimentosBD>> {


    //CALLBACK
    public dataListener callback;
    private String user;
    private String pass;
    private String matricula;
    private String inspeccion;
    private TextView cisterna;
    private Realm realm;
    //compartimentos
    private RealmResults<CACompartimentosBD> caCompartimentosBD;
    private List<Integer> compartimentos;
    private List<Integer> capacidad;
    private List<String> tags;
    private List<Integer> cantidad;
    private List<Boolean> cumpleCantidad = new ArrayList<>();
    private VerCompartimentosAdapter adapter;
    //FAB
    private FloatingActionButton fabCalculadora;
    private FloatingActionsMenu fabMenu;

    private RecyclerView mRecyclerView;
    private String soloVer = "0";

    public VerCompartimentosFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            callback = (dataListener) context;
        } catch (Exception e) {
            throw new ClassCastException(context.toString() + " should implement dataListener");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ver_compartimentos, container, false);
        cisterna = view.findViewById(R.id.tv_cisternamatricula);
        realm = Realm.getDefaultInstance();
        user = getArguments().getString("user", "no_user");
        pass = getArguments().getString("pass", "no_pass");
        //matricula = getArguments().getString("matricula", "sin_matricula");
        inspeccion = getArguments().getString("inspeccion", "sin_inspeccion");
        soloVer = getArguments().getString("soloVer", "0");

        fabCalculadora = view.findViewById(R.id.fabCalculadora);
        fabMenu = view.findViewById(R.id.grupoFab);
        fabCalculadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fabMenu.collapse();
                callback.abrirCalculadora();
            }
        });

        mRecyclerView = view.findViewById(R.id.rv_vercompartimentos);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        mRecyclerView.setHasFixedSize(true);

        renderCompartimentos();
        cisterna.setText(caCompartimentosBD.get(0).getMatricula());
        caCompartimentosBD.addChangeListener(this);
        return view;
    }


    public void renderCompartimentos() {

        caCompartimentosBD = realm.where(CACompartimentosBD.class).findAll();
        compartimentos = new ArrayList<>();
        capacidad = new ArrayList<>();
        tags = new ArrayList<>();
        cantidad = new ArrayList<>();

        if (caCompartimentosBD.size() > 0) {
            //Toast.makeText(getActivity(), "caCompartimentosBD.get(0).getCan_capacidad(): " + caCompartimentosBD.get(0).getCan_capacidad(), Toast.LENGTH_SHORT).show();

            for (int i = 0; i < caCompartimentosBD.size(); i++) {
                compartimentos.add(caCompartimentosBD.get(i).getCod_compartimento());
                capacidad.add(caCompartimentosBD.get(i).getCan_capacidad());
                tags.add(caCompartimentosBD.get(i).getCod_tag_cprt());
            }
        } else {
            Toast.makeText(getActivity(), "la query no da resultados...", Toast.LENGTH_SHORT).show();
        }

        adapter = new VerCompartimentosAdapter(R.layout.compartimentos_listview_item, caCompartimentosBD);
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onChange(RealmResults<CACompartimentosBD> caCompartimentosBDS) {
        adapter.notifyDataSetChanged();
    }


    public interface dataListener {
        void abrirCalculadora();
    }

}
