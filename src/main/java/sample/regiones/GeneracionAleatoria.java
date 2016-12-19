package sample.regiones;

import sample.tipos.Coordenada;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Created by jol on 17/12/16.
 */
public class GeneracionAleatoria {
    // area rectangular en la que generar los puntos aleatorios
    private double x0;
    private double y0;
    private double x1;
    private double y1;
    private String nombre;
    private String inicio;
    private String fin;

    // centro de visualizacion del mapa
    private double lat;
    private double lon;

    public GeneracionAleatoria(String nombre,String inicio, String fin,double lat,double lon,double x0, double y0, double x1, double y1) {
        this.x0=x0;
        this.y0=y0;
        this.x1=x1;
        this.y1=y1;
        this.lat=lat;
        this.lon=lon;
        this.nombre=nombre;
        this.inicio=inicio;
        this.fin=fin;
    }

    public double getX0() {
        return x0;
    }

    public void setX0(double x0) {
        this.x0 = x0;
    }

    public double getY0() {
        return y0;
    }

    public void setY0(double y0) {
        this.y0 = y0;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public List<Coordenada> generar(int numero) {
        Random rnd = new Random(System.currentTimeMillis());
        return IntStream.rangeClosed(1, numero).mapToObj(i -> {
            double x = x0 + rnd.nextDouble() * (x1 - x0);
            double y = y0 + rnd.nextDouble() * (y1 - y0);
            return new Coordenada(x, y);
        }).collect(Collectors.toList());
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return nombre;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFin() {
        return fin;
    }

    public void setFin(String fin) {
        this.fin = fin;
    }
}
