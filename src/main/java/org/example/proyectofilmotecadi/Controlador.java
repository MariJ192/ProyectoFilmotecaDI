package org.example.proyectofilmotecadi;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class Controlador implements Initializable {
    private final DatosFilmoteca datosFilmoteca = DatosFilmoteca.getInstancia();

    private ObservableList<Pelicula> listaPeliculas;
    @FXML
    private TableView<Pelicula> tableFilmoteca;

    @FXML
    public TextField buscadorPeliculas;
    @FXML
    public Label lblTit;
    @FXML
    public Label lblAnio;
    @FXML
    public Label lblDirector;
    @FXML
    public Label lblDescripcion;
    @FXML
    public Label lblVal;

    @FXML
    public ImageView imagePoster;
    @FXML
    public Button btnSalir;
    @FXML
    public Button btnNuevo;
    @FXML
    public Button btnMod;
    @FXML
    public Button btnDel;

    @FXML
    private TableColumn<Pelicula, Integer> columAnio;

    @FXML
    private TableColumn<Pelicula, String> columDirector;

    @FXML
    private TableColumn<Pelicula, Integer> columID;

    @FXML
    private TableColumn<Pelicula, String> columTitulo;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Verificación de que la TableView está correctamente enlazada
        if (tableFilmoteca == null) {
            System.out.println("tableFilmoteca no está enlazada correctamente.");
        } else {
            System.out.println("tableFilmoteca enlazada correctamente.");
        }

        // Obtener la lista de películas desde la instancia de DatosFilmoteca
        listaPeliculas = datosFilmoteca.getPeliculas();

        // Configurar las columnas de la tabla con las propiedades correspondientes
        columID.setCellValueFactory(new PropertyValueFactory<>("id"));
        columTitulo.setCellValueFactory(new PropertyValueFactory<>("title"));
        columAnio.setCellValueFactory(new PropertyValueFactory<>("year"));
        columDirector.setCellValueFactory(new PropertyValueFactory<>("director"));

        // Establecer los items de la TableView con la lista de películas
        tableFilmoteca.setItems(listaPeliculas);

        // Mostrar la cantidad de películas cargadas en la consola (puedes quitarlo después)
        System.out.println("Películas cargadas: " + listaPeliculas.size());

        // Listener para actualizar los detalles de la película seleccionada
        tableFilmoteca.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Actualizar los detalles en los labels
                lblTit.setText(newValue.getTitle());
                lblAnio.setText(String.valueOf(newValue.getYear()));
                lblDirector.setText(newValue.getDirector());
                lblVal.setText(String.valueOf(newValue.getRating()));
                lblDescripcion.setText(newValue.getDescription());

                // Actualizar el poster de la película
                String posterPath = newValue.getPoster();
                if (posterPath != null && !posterPath.isEmpty()) {
                    Image posterImage = new Image(posterPath);
                    imagePoster.setImage(posterImage);
                } else {
                    imagePoster.setImage(null); // Si no hay poster, poner la imagen nula
                }
            }
        });
    }

    public void btnCrearPelicula(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(Controlador.class.getResource("CrearPeliculaView.fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Crear Película");

            // Obtener el controlador de la ventana y no pasarle ninguna película (para crear)
            CrearEditarPeliculas crearEditarController = loader.getController();
            crearEditarController.inicializarFormulario(null);  // Le pasamos null para crear una nueva

            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Modificar una pelicula seleccionada
    public void btnModificarPelicula(ActionEvent actionEvent) {
        Pelicula peliculaSeleccionada = tableFilmoteca.getSelectionModel().getSelectedItem();

        if (peliculaSeleccionada != null) {
            // Abrir la ventana para editar la película seleccionada
            FXMLLoader loader = new FXMLLoader(Controlador.class.getResource("CrearPeliculaView.fxml"));
            try {
                Scene scene = new Scene(loader.load());
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Modificar Película");

                // Obtener el controlador de la ventana y pasarle la película seleccionada
                CrearEditarPeliculas crearEditarController = loader.getController();
                // Le pasamos la película seleccionada para editar
                crearEditarController.inicializarFormulario(peliculaSeleccionada);

                // Esperar a que la ventana de edición se cierre
                stage.showAndWait();

                // Después de cerrar la ventana de edición, actualizar los detalles en los labels y la TableView
                tableFilmoteca.refresh();

                // Actualizar los labels con los datos modificados de la película
                lblTit.setText(peliculaSeleccionada.getTitle());
                lblAnio.setText(String.valueOf(peliculaSeleccionada.getYear()));
                lblDirector.setText(peliculaSeleccionada.getDirector());
                lblVal.setText(String.valueOf(peliculaSeleccionada.getRating()));
                lblDescripcion.setText(peliculaSeleccionada.getDescription());

                // Actualizar el poster de la película
                String posterPath = peliculaSeleccionada.getPoster();
                if (posterPath != null && !posterPath.isEmpty()) {
                    Image posterImage = new Image(posterPath);
                    imagePoster.setImage(posterImage);
                } else {
                    imagePoster.setImage(null); // Si no hay poster, poner la imagen nula
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Si no se ha seleccionado una película, mostrar una alerta
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Seleccione una película para modificar.");
            alert.showAndWait();
        }
    }



    //Eliminar una pelicula
    public void btnEliminarPelicula(ActionEvent actionEvent) {
        int peliSeleccionada = tableFilmoteca.getSelectionModel().getSelectedIndex();

        if (peliSeleccionada != -1) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación de eliminación");
            alert.setHeaderText("¿Está seguro de que desea eliminar esta película?");

            // Mostramos la alerta y esperamos la respuesta
            ButtonType respuesta = alert.showAndWait().orElse(ButtonType.CANCEL);

            if (respuesta == ButtonType.OK) {
                // Si el usuario confirma, eliminamos la película
                tableFilmoteca.getItems().remove(peliSeleccionada);
                tableFilmoteca.refresh();
                datosFilmoteca.saveToJson();  // Guardar los cambios
            }
        } else {
            // Si no se ha seleccionado una película, mostramos una alerta
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Seleccione una película para eliminar.");
            alert.showAndWait();
        }
    }


    @FXML
    private void buscadorPeliculas(KeyEvent keyEvent) {
        String buscador = buscadorPeliculas.getText().toLowerCase().trim();

        if (buscador.isEmpty()) {
            // Si no hay texto en la búsqueda, mostrar todas las películas
            tableFilmoteca.setItems(datosFilmoteca.getPeliculas());
        } else {
            // Filtrar las películas que contienen el texto de búsqueda en su título
            List<Pelicula> filteredPeliculas = datosFilmoteca.getPeliculas().stream()
                    .filter(p -> p.getTitle().toLowerCase().contains(buscador))
                    .collect(Collectors.toList());

            tableFilmoteca.setItems(FXCollections.observableList(filteredPeliculas));
        }
    }
    //Salir de la aplicacion
    public void btnSalirApp(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmación de salida");
        alert.setHeaderText("¿Está seguro de que desea salir?");

        // Mostrar la alerta y esperar la respuesta
        ButtonType respuesta = alert.showAndWait().orElse(ButtonType.CANCEL);

        if (respuesta == ButtonType.OK) {
            // Si el usuario confirma la salida, cerrar la aplicación
            System.exit(0);
        }
    }

    }


