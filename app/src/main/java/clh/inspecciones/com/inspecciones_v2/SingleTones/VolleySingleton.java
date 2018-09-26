package clh.inspecciones.com.inspecciones_v2.SingleTones;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by root on 5/09/18.
 */

public class VolleySingleton {

    private static VolleySingleton instanciaVolley;
    private RequestQueue request;
    private static Context contexto;

    private VolleySingleton(Context context){
        contexto = context;
        request = getRequestQueue();
    }

    public RequestQueue getRequestQueue(){
        if (request == null)
        {
            request= Volley.newRequestQueue(contexto.getApplicationContext());
        }
        return request;
    }

    public static synchronized VolleySingleton getInstanciaVolley(Context context){
        if(instanciaVolley == null){
            instanciaVolley = new VolleySingleton(context);
        }
        return instanciaVolley;
    }

    public<T> void addToRequestqueue(Request<T> request){
        getRequestQueue().add(request);
    }
}
