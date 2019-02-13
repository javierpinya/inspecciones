package clh.inspecciones.com.inspecciones_v2.Clases;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;

import java.io.File;
import java.io.FileOutputStream;

public class DecodificaImagenClass extends AsyncTask<String, Integer, Void> {

    public String inspeccion;
    public int secuencial;
    public String ruta;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(String... lists) {
        this.secuencial = Integer.valueOf(lists[0]);
        this.inspeccion = lists[1];
        decodeImg(lists[2]);

        return null;

    }


    private String decodeImg(String foto){
        String fotoConRuta;
        byte[] decodedString = Base64.decode(foto, Base64.DEFAULT);
        // Bitmap Image
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        String filename = "foto_" + inspeccion + secuencial + ".png";
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        File dest = new File(file, filename);

        try {
            FileOutputStream out = new FileOutputStream(dest);
            bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        fotoConRuta = file + "/" + filename;
        return fotoConRuta;
    }

}
