package org.example.proyectofilmotecadi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.File;
import java.io.IOException;

public class DatosFilmoteca {
    private final ObjectMapper objectMapper;
    private static final String ARCHIVO_JSON = "datos/peliculas.json";
    public static DatosFilmoteca instancia = null;

    public final ObservableList<Pelicula> peliculas = FXCollections.observableArrayList();

    private DatosFilmoteca() {
        objectMapper = new ObjectMapper();
    }

    public static DatosFilmoteca getInstancia() {
        if (instancia == null) {
            instancia = new DatosFilmoteca();
        }
        return instancia;
    }
    public ObservableList<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void saveToJson() {
        // Formateamos el json
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        try {
            // Guardamos el cambio en el archivo JSON
            objectMapper.writeValue(new File(ARCHIVO_JSON), peliculas);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
