package clh.inspecciones.com.inspecciones_v2.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Clases.CARigidoBD;
import clh.inspecciones.com.inspecciones_v2.R;

/**
 * Created by root on 20/09/18.
 */

public class ControlAccesoResultadoRigidoAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<CARigidoBD> rigido;
    private DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
    private String transp_resp;

    public ControlAccesoResultadoRigidoAdapter(Context context, List<CARigidoBD> rigido, int layout ){
        this.context=context;
        this.rigido = rigido;
        this.layout = layout;
    }

    @Override
    public int getCount() {
        return rigido.size();
    }

    @Override
    public Object getItem(int position) {
        return rigido.get(position);
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
            vh.ejes = (TextView)convertView.findViewById(R.id.tv_ejestractora1);
            vh.mma = (TextView)convertView.findViewById(R.id.tv_mmatractora1);
            vh.ind_solo_gasoleos = (TextView)convertView.findViewById(R.id.tv_sologasoleostractora1);
            vh.ind_carga_pesados = (TextView)convertView.findViewById(R.id.tv_cargapesados1);
            vh.fec_cadu_calibracion = (TextView)convertView.findViewById(R.id.tv_tablacaltractora1);
            vh.cod_transportista_responsable = (TextView)convertView.findViewById(R.id.tv_transportistaresp1);
            vh.ind_bloqueo = (CheckBox) convertView.findViewById(R.id.cb_Bloqueadotractora);
            convertView.setTag(vh);
        } else {
           vh = (ViewHolder)convertView.getTag();
        }

        CARigidoBD caRigidoBD = rigido.get(position);

        if(caRigidoBD.getCod_transportista_resp() == null){
            transp_resp = "null";
        }else{
            transp_resp = caRigidoBD.getCod_transportista_resp();
        }




        vh.matricula.setText(caRigidoBD.getMatricula());
        vh.tipo_componente.setText(caRigidoBD.getTipo_componente());
        vh.adr.setText(df.format(caRigidoBD.getAdr()));
        vh.itv.setText(df.format(caRigidoBD.getItv()));
        vh.ejes.setText(String.valueOf(caRigidoBD.getEjes()));
        vh.ind_bloqueo.setChecked(caRigidoBD.isBloqueado());
        vh.mma.setText(String.valueOf(caRigidoBD.getMma())+ "Kgs");
        vh.tara.setText(String.valueOf(caRigidoBD.getTara())+ "Kgs");
        vh.chip.setText(String.valueOf(caRigidoBD.getChip()));
        vh.ind_solo_gasoleos.setText(caRigidoBD.getSoloGasoelos());
        vh.ind_carga_pesados.setText(caRigidoBD.getInd_carga_pesados());
        vh.fec_cadu_calibracion.setText(df.format(caRigidoBD.getFec_cadu_calibracion()));
        vh.cod_transportista_responsable.setText(transp_resp);
        vh.ind_bloqueo.setChecked(caRigidoBD.isBloqueado());



        return convertView;
    }

    public class ViewHolder{
        TextView matricula;
        TextView tipo_componente;
        TextView chip;
        TextView adr;
        TextView itv;
        TextView ejes;
        TextView tara;
        TextView mma;
        TextView ind_solo_gasoleos;
        TextView ind_carga_pesados;
        TextView fec_cadu_calibracion;
        TextView cod_transportista_responsable;
        CheckBox ind_bloqueo;
    }
}
