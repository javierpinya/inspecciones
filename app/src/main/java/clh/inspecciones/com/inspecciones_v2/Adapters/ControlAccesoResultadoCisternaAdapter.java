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
import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Clases.CACisternaBD;
import clh.inspecciones.com.inspecciones_v2.R;

/**
 * Created by root on 20/09/18.
 */

public class ControlAccesoResultadoCisternaAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<CACisternaBD> cisterna;
    private DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
    private String adr;

    public ControlAccesoResultadoCisternaAdapter(Context context, List<CACisternaBD> cisterna, int layout ){
        this.context=context;
        this.cisterna = cisterna;
        this.layout = layout;
    }



    @Override
    public int getCount() {
        return cisterna.size();
    }

    @Override
    public Object getItem(int position) {
        return cisterna.get(position);
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
            vh.tipo_componente = (TextView)convertView.findViewById(R.id.tv_tipotractora);
            vh.chip = (TextView)convertView.findViewById(R.id.tv_chiptractora);
            vh.adr = (TextView)convertView.findViewById(R.id.tv_adrtractora);
            vh.itv = (TextView)convertView.findViewById(R.id.tv_itvtractora);
            vh.tara = (TextView)convertView.findViewById(R.id.tv_taratractora);
            vh.ejes = (TextView)convertView.findViewById(R.id.tv_ejestractora);
            vh.mma = (TextView)convertView.findViewById(R.id.tv_mmatractora);
            vh.ind_solo_gasoleos = (TextView)convertView.findViewById(R.id.tv_sologasoleostractora);
            vh.ind_carga_pesados = (TextView)convertView.findViewById(R.id.tv_cargapesados);
            vh.fec_cadu_calibracion = (TextView)convertView.findViewById(R.id.tv_tablacaltractora);
            vh.cod_transportista_responsable = (TextView)convertView.findViewById(R.id.tv_transportistaresp1);
            vh.ind_bloqueo = (CheckBox) convertView.findViewById(R.id.cb_Bloqueadotractora);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder)convertView.getTag();
        }

        CACisternaBD caCisternaBD = cisterna.get(position);

        vh.matricula.setText(caCisternaBD.getMatricula().toString());
        vh.tipo_componente.setText(caCisternaBD.getTipo_componente());
        vh.adr.setText(caCisternaBD.getAdr().toString());
        vh.itv.setText(caCisternaBD.getItv().toString());
        vh.ejes.setText(String.valueOf(caCisternaBD.getEjes()));
        vh.ind_bloqueo.setChecked(caCisternaBD.isBloqueado());
        vh.mma.setText(String.valueOf(caCisternaBD.getMma()));
        vh.tara.setText(String.valueOf(caCisternaBD.getTara()));
        vh.chip.setText(String.valueOf(caCisternaBD.getChip()));
        vh.ind_solo_gasoleos.setText(caCisternaBD.getSoloGasoelos());
        vh.ind_carga_pesados.setText(caCisternaBD.getInd_carga_pesados());
        vh.fec_cadu_calibracion.setText(caCisternaBD.getFec_calibracion().toString());


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
