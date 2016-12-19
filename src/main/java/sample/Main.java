package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sample.algoritmos.*;
import sample.regiones.Madrid;
import sample.regiones.Malaga;
import sample.tipos.Coordenada;

import java.util.List;

public class Main extends Application {



    @Override
    public void start(Stage primaryStage) throws Exception {
        RegistroRegiones.getInstance().getListaRegiones().add(new Madrid());
        RegistroRegiones.getInstance().getListaRegiones().add(new Malaga());

        Madrid madrid=new Madrid();
        List<Coordenada> puntos = madrid.generar(RegistroBeans.getInstance().getBeanMenu().getNumeroCiudades());
        //List<Integer> solucion = ORTools.resolver(puntos);
        //Tsp.solve(100,0,100);


        Parent root = FXMLLoader.load(getClass().getResource("/menu.fxml"));
        primaryStage.setTitle("Comparación de optimizadores de rutas");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
