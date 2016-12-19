package sample;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.*;
import com.lynden.gmapsfx.service.geocoding.GeocodingService;
import com.sun.javafx.scene.layout.region.Margins;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.BuilderFactory;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;
import org.w3c.dom.Document;
import sample.regiones.GeneracionAleatoria;
import sample.tipos.Coordenada;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

//import com.lynden.gmapsfx.javascript.object.MapType;

public class ControladorMenu implements Initializable {

    @FXML
    private Button botonGenerar;

    @FXML
    private TextField fieldNumeroCiudades;

    @FXML
    private ComboBox<GeneracionAleatoria> fieldRegion;


    @Override
    public void initialize(URL url, ResourceBundle rb) {
        for (GeneracionAleatoria region:RegistroRegiones.getInstance().getListaRegiones()) {
            fieldRegion.getItems().add(region);
        }
        fieldRegion.getSelectionModel().selectFirst();

        botonGenerar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/pantallaPrincipal.fxml"));

                    Stage st1=new Stage();
                    st1.setTitle("Comparación de rutas optimizadas");
                    st1.setScene(new Scene(root, 1400, 900));
                    st1.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        RegistroBeans.getInstance().getBeanMenu().regionProperty().bind(fieldRegion.getSelectionModel().selectedItemProperty());

        SimpleIntegerProperty entero=new SimpleIntegerProperty();
        Bindings.bindBidirectional(fieldNumeroCiudades.textProperty(),entero,new NumberStringConverter());

        RegistroBeans.getInstance().getBeanMenu().numeroCiudadesProperty().bind(entero);
    }
}
