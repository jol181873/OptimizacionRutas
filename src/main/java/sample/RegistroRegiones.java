package sample;

import sample.regiones.GeneracionAleatoria;

import java.util.ArrayList;

/**
 * Created by jol on 18/12/16.
 */
public class RegistroRegiones {
    private static RegistroRegiones instance;
    private ArrayList<GeneracionAleatoria> listaRegiones=new ArrayList<>();
    private GeneracionAleatoria seleccionado;

    private RegistroRegiones() {
    }

    public static RegistroRegiones getInstance() {
        if (instance==null) {
            instance=new RegistroRegiones();
        }

        return instance;
    }

    public ArrayList<GeneracionAleatoria> getListaRegiones() {
        return listaRegiones;
    }

    public void setListaRegiones(ArrayList<GeneracionAleatoria> listaRegiones) {
        this.listaRegiones = listaRegiones;
    }

    public void seleccionar(GeneracionAleatoria selec) {
        seleccionado=selec;
    }
}
