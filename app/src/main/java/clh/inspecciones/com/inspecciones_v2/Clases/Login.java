package clh.inspecciones.com.inspecciones_v2.Clases;

import java.util.UUID;

/**
 * Created by root on 5/09/18.
 */

public class Login {

    private UUID id;
    private String usuario;
    private String password;

    public Login(){
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getPassword() {
        return password;
    }
}
