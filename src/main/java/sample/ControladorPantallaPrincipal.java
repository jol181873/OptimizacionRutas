package sample;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.*;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.algoritmos.ORTools;
import sample.regiones.GeneracionAleatoria;
import sample.tipos.Coordenada;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

//import com.lynden.gmapsfx.javascript.object.MapType;

public class ControladorPantallaPrincipal implements Initializable, MapComponentInitializedListener {

    @FXML
    private Button button;

    @FXML
    private GoogleMapView mapView;

    @FXML
    private GridPane panel;

    private GoogleMap map;

    //@Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
    }

    //@Override
    public void mapInitialized() {
        /*LatLong joeSmithLocation = new LatLong(47.6197, -122.3231);
        LatLong joshAndersonLocation = new LatLong(47.6297, -122.3431);
        LatLong bobUnderwoodLocation = new LatLong(47.6397, -122.3031);
        LatLong tomChoiceLocation = new LatLong(47.6497, -122.3325);
        LatLong fredWilkieLocation = new LatLong(47.6597, -122.3357);


        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(47.6097, -122.3331))
                //.mapType(MapType.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12);

        map = mapView.createMap(mapOptions);

        //Add markers to the map
        MarkerOptions markerOptions1 = new MarkerOptions();
        markerOptions1.position(joeSmithLocation);

        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(joshAndersonLocation);

        MarkerOptions markerOptions3 = new MarkerOptions();
        markerOptions3.position(bobUnderwoodLocation);

        MarkerOptions markerOptions4 = new MarkerOptions();
        markerOptions4.position(tomChoiceLocation);

        MarkerOptions markerOptions5 = new MarkerOptions();
        markerOptions5.position(fredWilkieLocation);

        Marker joeSmithMarker = new Marker(markerOptions1);
        Marker joshAndersonMarker = new Marker(markerOptions2);
        Marker bobUnderwoodMarker = new Marker(markerOptions3);
        Marker tomChoiceMarker= new Marker(markerOptions4);
        Marker fredWilkieMarker = new Marker(markerOptions5);

        map.addMarker( joeSmithMarker );
        map.addMarker( joshAndersonMarker );
        map.addMarker( bobUnderwoodMarker );
        map.addMarker( tomChoiceMarker );
        map.addMarker( fredWilkieMarker );

        InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
        infoWindowOptions.content("<h2>Fred Wilkie</h2>"
                + "Current Location: Safeway<br>"
                + "ETA: 45 minutes" );

        InfoWindow fredWilkeInfoWindow = new InfoWindow(infoWindowOptions);
        fredWilkeInfoWindow.open(map, fredWilkieMarker);
        */

        GeneracionAleatoria madrid=RegistroBeans.getInstance().getBeanMenu().getRegion();

        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        LatLong centro = new LatLong(40.425326, -3.703576);

        mapOptions.center(centro)
                //.mapType(MapType.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(12)
                .mapMarker(true);

        map = mapView.createMap(mapOptions,false);

        List<Coordenada> puntos = madrid.generar(RegistroBeans.getInstance().getBeanMenu().getNumeroCiudades());

        /*puntos.stream().map(var -> {
            return new LatLong(var.getLat(), var.getLon());
        }).map(var -> {
            MarkerOptions marker = new MarkerOptions();
            marker.position(var);
            return marker;
        }).map(var -> {
            Marker marker = new Marker(var);
            return marker;
        }).forEach(var->{
            map.addMarker(var);
        });
        */


        GeocodingService gs = new GeocodingService();
        ArrayList<DirectionsWaypoint> ways=new ArrayList<>();
        for (Coordenada coor:puntos) {
            /*gs.reverseGeocode(coor.getLat(), coor.getLon(), new GeocodingServiceCallback() {
                @Override
                public void geocodedResultsReceived(GeocodingResult[] results, GeocoderStatus status) {
                    suma();

                    System.out.println(status);
                    if (status==GeocoderStatus.OK) {
                        System.out.println(results[0].getFormattedAddress());
                        try {
                            DirectionsWaypoint direc=new DirectionsWaypoint(results[0].getFormattedAddress());
                            direc.setLocation(new LatLong(coor.getLat(),coor.getLon()));
                            ways.add(direc);
                            Thread.sleep(1000);
                        } catch (Exception es){
                            es.printStackTrace();
                        }
                    }
                }
            });
            */
            try {
                ways.add(new DirectionsWaypoint(new LatLong(coor.getLat(), coor.getLon())));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        System.out.println("Numero wayPoints: "+ways.size());

        DirectionsRequest dr=null;
        try {
            //dr= new DirectionsRequest(ways.get(0).getLocation(), ways.get(ways.size()-1).getLocation(), TravelModes.DRIVING, ways.subList(1, ways.size() - 2).toArray(new DirectionsWaypoint[0]));
            dr= new DirectionsRequest(madrid.getInicio(),madrid.getFin(),TravelModes.DRIVING, ways.toArray(new DirectionsWaypoint[0]));
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        DirectionsService servicio=new DirectionsService();
        try {
            servicio.getRoute(dr, new DirectionsServiceCallback() {
                @Override
                public void directionsReceived(DirectionsResult results, DirectionStatus status) {
                    System.out.println("Obtenida ruta mediante consulta a Google");
                    //results.getGeocodedWaypoints().stream().forEach(x -> System.out.println(x));
                    //map.showDirectionsPane();

                    try {
                        double distancia = 0;
                        double duracion = 0;
                        for (DirectionsLeg leg : results.getRoutes().get(0).getLegs()) {
                            distancia = distancia + leg.getDistance().getValue();
                            System.out.println("Distancia parcial: " + leg.getDistance().getValue());

                            if (leg!=null && leg.getDuration()!=null ) {
                                //duracion = duracion + leg.getDuration().getValue();
                                System.out.println("Tiempo parcial   : " + leg.getDuration().getText());
                            }
                        }
                        System.out.println("Distancia total: " + distancia);
                        //System.out.println("Tiempo total   : " + duracion);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }




                }
            }, new DirectionsRenderer(true, map, mapView.getDirec()));





             // mapa OR-Tools
            GoogleMapView mapView2=new GoogleMapView();
            mapView2.addMapInializedListener(new MapComponentInitializedListener() {
                @Override
                public void mapInitialized() {
                    MapOptions mapOptions = new MapOptions();

                    mapOptions.center(new LatLong(40.425326, -3.703576))
                            //.mapType(MapType.ROADMAP)
                            .overviewMapControl(false)
                            .panControl(false)
                            .rotateControl(false)
                            .scaleControl(false)
                            .streetViewControl(false)
                            .zoomControl(false)
                            .zoom(12);

                    GoogleMap map = mapView2.createMap(mapOptions);

                    List<Integer> resolver = ORTools.resolver(puntos);

                    for (int pos:resolver) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position( new LatLong(puntos.get(pos).getLat(),puntos.get(pos).getLon()) )
                                .visible(Boolean.TRUE)
                                .title(String.valueOf(pos));

                        Marker marker = new Marker( markerOptions );
                        map.addMarker(marker);
                    }



                    panel.add(mapView2,1,0);
                }
            });
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}
