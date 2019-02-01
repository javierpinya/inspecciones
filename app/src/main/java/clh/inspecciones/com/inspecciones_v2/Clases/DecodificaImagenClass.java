package clh.inspecciones.com.inspecciones_v2.Clases;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Base64;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class DecodificaImagenClass extends AsyncTask<String, Integer, String> {

    public String inspeccion;
    public int secuencial;
    public String ruta;

    @Override
    protected String doInBackground(String... strings) {
        String ruta="";
        this.inspeccion = strings[1];
        this.secuencial = Integer.valueOf(strings[2]);
        ruta = decodeImg(strings[0]);
        return ruta;
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String ruta) {
        //super.onPostExecute(s);
        asignaRuta(ruta);

    }

    public void asignaRuta(String ruta) {
        this.ruta=ruta;

    }

    public String getRuta(){
        return ruta;
    }

    private String decodeImg(String foto){
        String fotoConRuta;
        byte[] decodedString = Base64.decode(foto, Base64.DEFAULT);
        // Bitmap Image
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        String filename = "foto_" + inspeccion + "_" + secuencial + ".png";
        File file= Environment.getExternalStorageDirectory();
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
