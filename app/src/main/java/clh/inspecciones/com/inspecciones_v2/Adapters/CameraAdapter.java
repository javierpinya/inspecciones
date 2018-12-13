package clh.inspecciones.com.inspecciones_v2.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.List;

import clh.inspecciones.com.inspecciones_v2.R;

public class CameraAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Bitmap> bitmaps;

    public CameraAdapter(Context context, int layout, List<Bitmap> bitmaps) {
        this.context = context;
        this.layout = layout;
        this.bitmaps = bitmaps;
    }

    @Override
    public int getCount() {
        return bitmaps.size();
    }

    @Override
    public Object getItem(int position) {
        return bitmaps.get(position);
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

        vh.imageView.setImageBitmap(bitmaps.get(position));

        return convertView;
    }

    public class ViewHolder{
        ImageView imageView;
    }
}
