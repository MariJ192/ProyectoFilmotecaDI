package org.example.proyectofilmotecadi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class CrearEditarPeliculas {
    private final DatosFilmoteca datosFilmoteca = DatosFilmoteca.getInstancia();
    private ObservableList<Pelicula> listaPeliculas;
    @FXML
    private TableView<Pelicula> tableFilmoteca;
    Pelicula pelicula;
    Stage titStage;

    @FXML
    public Label lblTituloWind;
    @FXML
    public Label lblRating;
    @FXML
    public TextField textGenero;
    @FXML
    public TextField txtPoster;
    @FXML
    public TextField txtDescripcion;
    @FXML
    public TextField textDirector;
    @FXML
    public TextField txtAnio;
    @FXML
    public TextField txtTitulo;
    @FXML
    public Slider sliderRating;
    @FXML
    public Button btnAceptar;
    @FXML
    public Button btnCancelar;

    public void inicializarFormulario(Pelicula pelicula) {
        // Restringir txtAnio para que solo acepte números
        txtAnio.setOnKeyTyped(event -> {
            if (!event.getCharacter().matches("[0-9]")) {
                event.consume(); // Bloquea la entrada del carácter no válido
            }
        });

        // Evitar que se peguen caracteres no numéricos con Ctrl+V
        txtAnio.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                txtAnio.setText(oldValue); // Revierte el cambio si hay caracteres no numéricos
            }
        });
        // Configuración inicial del título del stage y etiqueta
        titStage = (Stage) lblTituloWind.getScene().getWindow();
        if (pelicula != null) {
            titStage.setTitle("Editar Película");
            lblTituloWind.setText("MODIFICAR UNA PELICULA");
        } else {
            titStage.setTitle("Añadir Película");
            lblTituloWind.setText("AÑADIR UNA PELICULA");
        }

        if (pelicula != null) {
            // Cargar datos de la película en los campos
            this.pelicula = pelicula;

            txtTitulo.setText(pelicula.getTitle());
            txtAnio.setText(String.valueOf(pelicula.getYear()));
            textDirector.setText(pelicula.getDirector());
            txtDescripcion.setText(pelicula.getDescription());
            sliderRating.setValue(pelicula.getRating());
            txtPoster.setText(pelicula.getPoster());

            // Mostrar el valor inicial del slider en lblRating
            lblRating.setText(formatRating(sliderRating.getValue()));
        } else {
            // Configuración inicial para crear una nueva película
            this.pelicula = null;

            // Mostrar el valor inicial del slider (0.0) en lblRating
            lblRating.setText(formatRating(sliderRating.getValue()));
        }

        // Listener para actualizar lblRating dinámicamente al mover el slider
        sliderRating.valueProperty().addListener((observable, oldValue, newValue) -> {
            // Redondear y formatear con un decimal
            lblRating.setText(formatRating(newValue.doubleValue()));
        });
    }
    private String formatRating(double value) {
        double roundedValue = Math.round(value * 10.0) / 10.0;
        return String.format("%.1f", roundedValue);
    }

    @FXML
    public void agregarPelicula(ActionEvent event) {
        if (!isValidURL(txtPoster.getText())) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("URL no válida");
            alert.setHeaderText("Error en la URL del póster");
            alert.setContentText("Por favor, ingrese una URL válida.");
            alert.showAndWait();
            return; // No continúa si la URL no es válida
        }

        if (pelicula == null) {
            lblTituloWind.setText("AÑADIR UNA PELICULA");
            // Crear una nueva película
            listaPeliculas = datosFilmoteca.getPeliculas();
            if (listaPeliculas == null) {
                listaPeliculas = FXCollections.observableArrayList();
            }

            // Crear la nueva película
            pelicula = new Pelicula();
            pelicula.setId(listaPeliculas.size() + 1);
            pelicula.setTitle(txtTitulo.getText());
            pelicula.setDirector(textDirector.getText());
            pelicula.setDescription(txtDescripcion.getText());
            pelicula.setYear(Integer.parseInt(txtAnio.getText()));
            pelicula.setPoster(txtPoster.getText());
            pelicula.setRating(Math.round(sliderRating.getValue() * 10.0) / 10.0); // Redondear antes de guardar

            // Añadir la película a la lista
            listaPeliculas.add(pelicula);
            datosFilmoteca.saveToJson();  // Guardar en el archivo JSON

        } else {
            // Editar una película existente
            pelicula.setTitle(txtTitulo.getText());
            pelicula.setDirector(textDirector.getText());
            pelicula.setDescription(txtDescripcion.getText());
            pelicula.setYear(Integer.parseInt(txtAnio.getText()));
            pelicula.setPoster(txtPoster.getText());
            pelicula.setRating(Math.round(sliderRating.getValue() * 10.0) / 10.0); // Redondear antes de guardar

            // Guardar los cambios en el archivo JSON
            datosFilmoteca.saveToJson();
        }

        // Cerrar la ventana después de agregar o editar
        titStage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        titStage.close();
    }

    @FXML
    public void cancelarPelicula(ActionEvent event) {
        // Cerrar la ventana actual
        titStage = (Stage) ((Node) (event.getSource())).getScene().getWindow();
        titStage.close();
    }
    private boolean isValidURL(String url) {
        String urlRegex = "^(https?|ftp)://[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}(:[0-9]{1,5})?(/.*)?$";
        return url.matches(urlRegex);
    }
}
