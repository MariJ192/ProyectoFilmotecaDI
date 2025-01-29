package org.example.proyectofilmotecadi;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Aplicacion extends Application {
    private DatosFilmoteca datosFilmoteca = DatosFilmoteca.getInstancia();
    private ObservableList<Pelicula> listFilmoteca = datosFilmoteca.getPeliculas();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Aplicacion.class.getResource("View.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 890, 620);
        stage.setTitle("Filmoteca");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void init() throws Exception {
        super.init();

        // Cargamos la librería jackson
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            File peliculas = new File("datos/peliculas.json");
            List<Pelicula> listPeliculas = objectMapper.readValue(peliculas, new TypeReference<List<Pelicula>>() {});

            // Añadimos las peliculas a la observableList
            listFilmoteca.addAll(listPeliculas);
        } catch (Exception e) {
            System.out.println("Error al leer los datos: " + e.getMessage());
        }
    }

    @Override
    public void stop() throws Exception {
        super.stop();
    }
}
