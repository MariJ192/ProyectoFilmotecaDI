module org.example.proyectofilmotecadi {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.core;


    opens org.example.proyectofilmotecadi to javafx.fxml;
    exports org.example.proyectofilmotecadi;
}