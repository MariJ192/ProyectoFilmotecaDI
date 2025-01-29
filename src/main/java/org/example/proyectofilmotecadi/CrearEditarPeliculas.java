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

    /**
     * Método para redondear y formatear el valor del rating con un solo decimal.
     *
     * @param value El valor original del slider.
     * @return El valor formateado con un decimal como String.
     */
    private String formatRating(double value) {
        double roundedValue = Math.round(value * 10.0) / 10.0;
        return String.format("%.1f", roundedValue);
    }

    @FXML
    public void agregarPelicula(ActionEvent event) {
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
}
