package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import clh.inspecciones.com.inspecciones_v2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FotoCompletaFragment extends Fragment {


    private String foto;
    private Bitmap imagen;
    private ImageView fotoCompleta;

    public FotoCompletaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_foto_completa, container, false);
        fotoCompleta = view.findViewById(R.id.fotoCompleta);

        foto = getArguments().getString("imagen", "sin_datos_inspeccion");

        imagen = StringToBitMap(foto);

        fotoCompleta.setImageBitmap(imagen);

        // Inflate the layout for this fragment
        return view;
    }


    public Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

}
