package clh.inspecciones.com.inspecciones_v2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Clases.CACompartimentosBD;
import clh.inspecciones.com.inspecciones_v2.R;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;


public class CompartimentosAdapter extends BaseAdapter implements RealmChangeListener<RealmResults<CACompartimentosBD>>, View.OnClickListener {

    private int layout;
    private Context context;
    private String matricula;
    private RealmResults<CACompartimentosBD> compartimentosBDS;
    private List<Integer> capacidad;
    private List<Integer> compartimento;
    private List<String> tags;
    private List<Integer> cant_cargada;

    private Realm realm;


    public CompartimentosAdapter( Context context, int layout, String matricula, List<Integer> capacidad, List<Integer> compartimento, List<String> tags){
        this.context = context;
        this.layout = layout;
        this.matricula = matricula;
        this.capacidad = capacidad;
        this.compartimento = compartimento;
        this.cant_cargada = new ArrayList<>();
        this.tags = tags;
        realm = Realm.getDefaultInstance();
    }

    @Override
    public int getCount() {
        return compartimento.size();
    }

    @Override
    public Object getItem(int position) {
        return compartimento.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(layout,null);


        TextView tv_numcomp;
        TextView tv_codtag;
        TextView tv_capacidadtotalcomp1;
        EditText et_cant_cargada;
        Button btn_guardar;

        tv_numcomp = (TextView)convertView.findViewById(R.id.tv_numcomp);
        tv_codtag = (TextView)convertView.findViewById(R.id.tv_codtag);
        tv_capacidadtotalcomp1 = (TextView)convertView.findViewById(R.id.tv_capacidadtotalcomp1);
        et_cant_cargada = (EditText)convertView.findViewById(R.id.et_cant_cargada);
        btn_guardar = (Button)convertView.findViewById(R.id.btn_guardar);


        tv_numcomp.setText(compartimento.get(position).toString());
        tv_codtag.setText(tags.get(position));
        tv_capacidadtotalcomp1.setText(capacidad.get(position));

        cant_cargada.add(Integer.valueOf(et_cant_cargada.getText().toString()));
        btn_guardar.setOnClickListener(this);



        return convertView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_guardar:
                compartimentosBDS = realm.where(CACompartimentosBD.class).findAll();
                realm.beginTransaction();



        }

    }
/*
    public class viewHolder{
        TextView tv_numcomp;
        TextView tv_codtag;
        TextView tv_capacidadtotalcomp1;
        EditText et_cant_cargada;
        Button btn_guardar;

    }*/

    @Override
    public void onChange(RealmResults<CACompartimentosBD> caCompartimentosBDS) {

    }
}
