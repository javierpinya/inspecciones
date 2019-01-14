package clh.inspecciones.com.inspecciones_v2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Clases.CATractoraBD;
import clh.inspecciones.com.inspecciones_v2.R;

/**
 * Created by root on 19/09/18.
 */

public class ControlAccesoResultadoTractoraAdapter extends BaseAdapter {

    private Context context;
    private List<CATractoraBD> caTractora;
    private int layout;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    private String transp_resp;



    public ControlAccesoResultadoTractoraAdapter(Context context,List<CATractoraBD> caTractora, int layout ){
        this.context=context;
        this.caTractora = caTractora;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return caTractora.size();
    }

    @Override
    public Object getItem(int position) {
        return caTractora.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;
        if(convertView == null){
            convertView = LayoutInflater.from(context).inflate(layout,null);
            vh = new ViewHolder();
            vh.matricula = (TextView)convertView.findViewById(R.id.tv_tractoramatricula);
            vh.tipo_componente = (TextView)convertView.findViewById(R.id.tv_tipotractora1);
            vh.chip = (TextView)convertView.findViewById(R.id.tv_chiptractora1);
            vh.adr = (TextView)convertView.findViewById(R.id.tv_adrtractora1);
            vh.itv = (TextView)convertView.findViewById(R.id.tv_itvtractora1);
            vh.tara = (TextView)convertView.findViewById(R.id.tv_taratractora1);
            vh.mma = (TextView)convertView.findViewById(R.id.tv_mmatractora1);
            vh.ind_solo_gasoleos = (TextView)convertView.findViewById(R.id.tv_sologasoleostractora1);
            vh.cod_transportista_responsable = (TextView)convertView.findViewById(R.id.tv_transportistaresp1);
            vh.ind_bloqueo = (CheckBox) convertView.findViewById(R.id.cb_Bloqueadotractora);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder)convertView.getTag();
        }

        CATractoraBD caTractoraBD = caTractora.get(position);
        if(caTractoraBD.getCod_transportista_resp() == null){
            transp_resp = "null";
        }else{
            transp_resp = caTractoraBD.getCod_transportista_resp();
        }



        vh.matricula.setText(caTractoraBD.getMatricula().toString());
        vh.tipo_componente.setText(caTractoraBD.getTipo_componente());
        vh.adr.setText(df.format(caTractoraBD.getAdr()));
        vh.itv.setText(df.format(caTractoraBD.getItv()));
        vh.mma.setText(String.valueOf(caTractoraBD.getMma()) + "Kgs");
        vh.tara.setText(String.valueOf(caTractoraBD.getTara()) + "Kgs");
        vh.chip.setText(String.valueOf(caTractoraBD.getChip()));
        vh.ind_solo_gasoleos.setText(caTractoraBD.getSoloGasoleos());
        vh.cod_transportista_responsable.setText(caTractoraBD.getCod_transportista_resp());
        vh.ind_bloqueo.setChecked(caTractoraBD.isBloqueado());


        return convertView;
    }

    public class ViewHolder{
        TextView matricula;
        TextView tipo_componente;
        TextView chip;
        TextView adr;
        TextView itv;
        TextView tara;
        TextView mma;
        TextView ind_solo_gasoleos;
        TextView cod_transportista_responsable;
        CheckBox ind_bloqueo;
    }
}
