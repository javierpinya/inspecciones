package clh.inspecciones.com.inspecciones_v2.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import clh.inspecciones.com.inspecciones_v2.R;

public class FotosAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Uri> miPath;

    public FotosAdapter(Context context, int layout, List<Uri> path) {
        this.context = context;
        this.layout = layout;
        this.miPath = path;
    }

    @Override
    public int getCount() {
        return miPath.size();
    }

    @Override
    public Object getItem(int position) {
        return miPath.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView==null){
            convertView = LayoutInflater.from(this.context).inflate(this.layout,null);
            vh = new ViewHolder();
            vh.imageView = (ImageView)convertView.findViewById(R.id.image1);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder)convertView.getTag();
        }

        vh.imageView.setImageURI(miPath.get(position));
        //vh.imageView = fotos.get(position);
        return convertView;
    }

    public class ViewHolder{
        ImageView imageView;
    }
}
