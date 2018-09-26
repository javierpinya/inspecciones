package clh.inspecciones.com.inspecciones_v2.Clases;

import java.util.UUID;

/**
 * Created by root on 6/09/18.
 */

public class Menu {

    private UUID id;

    public Menu(){
        id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }
}
