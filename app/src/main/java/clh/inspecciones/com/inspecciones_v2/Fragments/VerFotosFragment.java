package clh.inspecciones.com.inspecciones_v2.Fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import clh.inspecciones.com.inspecciones_v2.Adapters.CameraAdapter;
import clh.inspecciones.com.inspecciones_v2.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class VerFotosFragment extends Fragment {

    public GridView gridView;
    private File file;
    private String user;
    private String pass;
    private String inspeccion;
    private Integer numFotos;
    private String RUTA_IMAGEN = "";
    private Bitmap bitmap;
    private List<Bitmap> bitmaps = new ArrayList<>();
    private List<Uri> path;
    private CameraAdapter myCameraAdapter;

    public VerFotosFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ver_fotos, container, false);

        gridView = view.findViewById(R.id.gridViewDescargaFotos);
        user = getArguments().getString("user", "sin_datos_user");
        pass = getArguments().getString("pass", "sin_datos_pass");
        inspeccion = getArguments().getString("inspeccion", "sin_datos_inspeccion");
        numFotos = Integer.valueOf(getArguments().getString("numFotosDescargadas", "0"));
        cargarFotos();
        // Inflate the layout for this fragment
        return view;
    }

    private void cargarFotos() {
        String filename;
        String storageDir;

        for (int i = 1; i <= numFotos; i++) {
            filename = "foto_" + inspeccion + 1 + ".png";
            storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + File.separator + filename;
            bitmap = BitmapFactory.decodeFile(storageDir);
            bitmaps.add(bitmap);
        }

        establecerImagenes(bitmaps);
    }

    private void establecerImagenes(List<Bitmap> bitmap) {
        myCameraAdapter = new CameraAdapter(getActivity(), R.layout.list_fotos, bitmap);
        gridView.setAdapter(myCameraAdapter);
        myCameraAdapter.notifyDataSetChanged();
    }
}
