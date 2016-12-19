package sample;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import sample.regiones.GeneracionAleatoria;

/**
 * Created by jol on 18/12/16.
 */
public class BeanMenu {
    private IntegerProperty numeroCiudades;
    private SimpleObjectProperty<GeneracionAleatoria> region;

    public BeanMenu() {
        this.numeroCiudades = new SimpleIntegerProperty();
        this.region=new SimpleObjectProperty<GeneracionAleatoria>();
    }

    public int getNumeroCiudades() {
        return numeroCiudades.get();
    }

    public IntegerProperty numeroCiudadesProperty() {
        return numeroCiudades;
    }

    public void setNumeroCiudades(int numeroCiudades) {
        this.numeroCiudades.set(numeroCiudades);
    }

    public GeneracionAleatoria getRegion() {
        return region.get();
    }

    public SimpleObjectProperty<GeneracionAleatoria> regionProperty() {
        return region;
    }

    public void setRegion(GeneracionAleatoria region) {
        this.region.set(region);
    }
}
